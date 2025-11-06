package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.routine.Patient_Routine;
import com.example.elderexserver.data.webSocket.*;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketSessionService {
    @Value("${websocket.session.timeout.minutes}")
    private int sessionTimeoutMinutes;

    private final ClassificationService classificationService;
    private final PatientRoutineService patientRoutineService;
    private final ExerciseSessionService exerciseSessionService;
    private final ExerciseSessionDetailService exerciseSessionDetailService;

    private Cache<String, OngoingSession> sessionCache;

    @PostConstruct
    private void initCache() {
        this.sessionCache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sessionTimeoutMinutes))
                .removalListener((String key, OngoingSession value, RemovalCause cause) -> {
                    if (cause == RemovalCause.EXPIRED) {
                        handleExpiredSession(key, value);
                    }
                })
                .build();
    }

    public OngoingSession getSession(String sessionId) {
        return sessionCache.getIfPresent(sessionId);
    }

    public void invalidateSession(String sessionId) {
        sessionCache.invalidate(sessionId);
        log.info("Invalidated session {}", sessionId);
    }

    public SessionUpdateResult updateSession(String sessionId, Integer userId, String userName, ExerciseDataEvent event) {

        log.info("Processing session {} for user {} with {} features",
                sessionId,
                userName,
                event.getData().getFeatures().size());

        FeaturesResponse classified = classificationService.classify(event.getData());

        OngoingSession ongoingSession = sessionCache.get(sessionId,
                k -> createNewSession(userId, event.getStartTime()));

        if (ongoingSession == null) {
            log.error("Failed to create session for sessionId: {}", sessionId);
            throw new IllegalStateException("Session creation failed");
        }

        synchronized (ongoingSession) {
            ongoingSession.incrementExerciseCount(classified.getExercise_id(), classified.getExercise_name());
            Exercise_Session_Detail detail = exerciseSessionDetailService.createSessionDetail(classified, event);
            ongoingSession.addSessionDetail(detail);
        }

        return new SessionUpdateResult(
                classified.getExercise_id(),
                ongoingSession.getExercises().get(classified.getExercise_id()).getName(),
                ongoingSession.getExercises().get(classified.getExercise_id()).getCount()
        );
    }

    private OngoingSession createNewSession(Integer userId, LocalDateTime startTime) {
        OngoingSession ongoingSession = new OngoingSession();
        ongoingSession.setExercises(new ConcurrentHashMap<>());

        Exercise_Session session = new Exercise_Session();

        Patient_Routine patientRoutine = patientRoutineService.getCurrentPatientRoutineByPatientId(userId);
        if (patientRoutine == null) {
            throw new IllegalStateException("No active patient routine found for patient id " + userId);
        }

        session.setPatientRoutine(patientRoutine);
        session.setStart_time(startTime);
        session.setExercise_session_details(new ArrayList<>());

        ongoingSession.setSession(session);
        return ongoingSession;
    }

    private void handleExpiredSession(String sessionId, OngoingSession expiredSession) {
        if (expiredSession == null) return;
        log.warn("⚠️ Session expired: SessionID={}, ExerciseCount={}",sessionId, expiredSession.getExercises().size());
        try {
            exerciseSessionService.saveSessionToDatabase(sessionId, expiredSession, LocalDateTime.now());
        } catch (Exception e) {
            log.error("Failed to save expired session {}: {}", sessionId, e.getMessage(), e);
        }
    }


    public SessionResultResponse finalizeSession(String sessionId, LocalDateTime endTime) {
        OngoingSession ongoingSession = sessionCache.getIfPresent(sessionId);

        if (ongoingSession == null) {
            log.warn("No session data found for sessionId: {}. Returning empty result.", sessionId);
            return createEmptyResult(endTime);
        }

        try {
            SessionResultResponse response;

            synchronized (ongoingSession) {
                response = createSessionResult(ongoingSession, endTime);
            }

            exerciseSessionService.saveSessionToDatabase(sessionId, ongoingSession, endTime);
            sessionCache.invalidate(sessionId);

            log.info("Finalized and cleaned up session: {}", sessionId);

            return response;
        } catch (Exception e) {
            log.error("Error finalizing session {}: {}", sessionId, e.getMessage(), e);
            throw new RuntimeException("Failed to finalize session", e);
        }
    }

    private SessionResultResponse createEmptyResult(LocalDateTime endTime) {
        return new SessionResultResponse(
                "session_result",
                endTime,
                new ArrayList<>()
        );
    }

    private SessionResultResponse createSessionResult(OngoingSession ongoingSession, LocalDateTime endTime) {
        ConcurrentHashMap<Integer, OngoingSession.Exercise> counts = ongoingSession.getExercises();

        List<SessionResultResponse.SessionExercis> exercises = new ArrayList<>();
        for (Map.Entry<Integer, OngoingSession.Exercise> entry : counts.entrySet()) {
            exercises.add(new SessionResultResponse.SessionExercis(
                    entry.getKey(),
                    entry.getValue().getName(),
                    entry.getValue().getCount()
            ));
        }

        log.info("📤 Created final result: {} Exercises", exercises);

        return new SessionResultResponse("session_result", endTime, exercises);
    }
}
