package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.data.exercise.DTO.FeaturesResponse;
import com.example.elderexserver.data.exercise.DTO.OngoingSession;
import com.example.elderexserver.data.exercise.DTO.SessionResultResponse;
import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.UserPrincipal;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {

    @Value("${websocket.session.timeout.minutes}")
    private int sessionTimeoutMinutes;

    private final ClassificationService classificationService;
    private final SimpMessagingTemplate messagingTemplate;

    private final PatientRoutineService patientRoutineService;
    private final ExerciseSessionService exerciseSessionService;

    private Cache<String, OngoingSession> sessionCounts;

    @PostConstruct
    private void initCache() {
        this.sessionCounts = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofMinutes(sessionTimeoutMinutes))
                .removalListener((String key, OngoingSession value, RemovalCause cause) -> {
                    if (cause == RemovalCause.EXPIRED) {
                        handleExpiredSession(key, value);
                    }
                })
                .build();
    }

    private UserPrincipal asUserPrincipal(Principal principal) {
        if (principal instanceof UserPrincipal userPrincipal) {
            return userPrincipal;
        }
        log.error("Invalid principal type: {}. Expected UserPrincipal.", principal.getClass().getName());
        throw new IllegalStateException("Invalid principal type. Expected UserPrincipal.");
    }

    private String getSessionId(Principal principal) {
        return asUserPrincipal(principal).getSessionId();
    }

    public void handleExerciseData(ExerciseDataEvent event, Principal principal) {
        String sessionId = getSessionId(principal);

        if ("session_end".equals(event.getType())) {
            sendFinalResult(principal, sessionId, event.getEndTime());
            return;
        }

        log.debug("Processing session {} for user {} with {} features",
                sessionId,
                principal.getName(),
                event.getData().getFeatures().size());

        FeaturesResponse response = classificationService.classify(event.getData());

        OngoingSession ongoingSession = sessionCounts.get(sessionId,
                k -> createNewSession(principal, event.getStartTime()));

        if (ongoingSession == null) {
            log.error("Failed to create session for sessionId: {}", sessionId);
            throw new IllegalStateException("Session creation failed");
        }

        synchronized (ongoingSession) {
            ongoingSession.incrementExerciseCount(response.getExercise_id());
            Exercise_Session_Detail detail = createSessionDetail(response, event);
            ongoingSession.addSessionDetail(detail);
        }
        log.debug("Updated counts for session {}: {}", sessionId, ongoingSession.getCount());
    }

    public void sendResultToClient(Principal principal, SessionResultResponse result) {
        try {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/topic/exercises",
                    result
            );
            log.debug("Sent result to user {}", principal.getName());
        } catch (Exception e) {
            log.error("Failed to send result to user {}: {}", principal.getName(), e.getMessage(), e);
        }
    }

    private Exercise_Session_Detail createSessionDetail(FeaturesResponse response, ExerciseDataEvent event) {
        Exercise_Session_Detail detail = new Exercise_Session_Detail();
        detail.setExercise_id(response.getExercise_id());
        detail.setReps(1);
        detail.setStart_time(event.getStartTime());
        detail.setEnd_time(event.getEndTime());
        return detail;
    }

    private void sendFinalResult(Principal principal, String sessionId, LocalDateTime endTime) {
        OngoingSession ongoingSession = sessionCounts.getIfPresent(sessionId);

        if (ongoingSession == null) {
            log.warn("No session data found for sessionId: {}. Sending empty result.", sessionId);
            sendEmptyResult(principal, endTime);
            return;
        }

        try {
            synchronized (ongoingSession) {
                SessionResultResponse response = createSessionResult(principal, ongoingSession, endTime);
                sendResultToClient(principal, response);

                exerciseSessionService.saveSessionToDatabase(sessionId, ongoingSession, endTime);

                sessionCounts.invalidate(sessionId);
                log.debug("Cleaned up session data for: {}", sessionId);
            }
        } catch (Exception e) {
            log.error("Error processing final result for session {}: {}", sessionId, e.getMessage(), e);
        }
    }

    private OngoingSession createNewSession(Principal principal, LocalDateTime startTime) {
        OngoingSession ongoingSession = new OngoingSession();
        ongoingSession.setCount(new ConcurrentHashMap<>());

        Exercise_Session session = new Exercise_Session();
        UserPrincipal userPrincipal = asUserPrincipal(principal);
        Integer patientId = userPrincipal.getUserId();

        var patientRoutine = patientRoutineService.getCurrentPatientRoutineByPatientId(patientId);
        if (patientRoutine == null) {
            throw new IllegalStateException("No active routine found for patient: " + patientId);
        }

        session.setPatientRoutine(patientRoutine);
        session.setStart_time(startTime);
        session.setExercise_session_details(new ArrayList<>());

        ongoingSession.setSession(session);
        return ongoingSession;
    }

    private void handleExpiredSession(String sessionId, OngoingSession expiredSession) {
        if (expiredSession == null) return;
        log.warn("⚠️ Session expired: SessionID={}, ExerciseCount={}",sessionId, expiredSession.getCount().size());
        try {
            exerciseSessionService.saveSessionToDatabase(sessionId, expiredSession, LocalDateTime.now());
        } catch (Exception e) {
            log.error("Failed to save expired session {}: {}", sessionId, e.getMessage(), e);
        }
    }

    private void sendEmptyResult(Principal principal, LocalDateTime endTime) {
        SessionResultResponse emptyResponse = new SessionResultResponse(
                "session_result",
                endTime,
                new ArrayList<>()
        );
        sendResultToClient(principal, emptyResponse);
    }

    private SessionResultResponse createSessionResult( Principal principal, OngoingSession ongoingSession, LocalDateTime endTime) {
        ConcurrentHashMap<Integer, Integer> counts = ongoingSession.getCount();

        List<SessionResultResponse.SessionExercis> exercises = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            exercises.add(new SessionResultResponse.SessionExercis(
                    String.valueOf(entry.getKey()),
                    entry.getValue()
            ));
        }

        UserPrincipal user = asUserPrincipal(principal);
        log.info("📤 Created final result: UserID={}, SessionID={}, Exercises={}, TotalReps={}",
                user.getUserId(),
                user.getSessionId(),
                exercises.size(),
                counts.values().stream().mapToInt(Integer::intValue).sum());

        return new SessionResultResponse("session_result", endTime, exercises);
    }
}