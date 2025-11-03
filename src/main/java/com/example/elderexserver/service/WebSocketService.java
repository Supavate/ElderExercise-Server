package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.data.exercise.DTO.FeaturesResponse;
import com.example.elderexserver.data.exercise.DTO.OngoingSession;
import com.example.elderexserver.data.exercise.DTO.SessionResultResponse;
import com.example.elderexserver.data.exercise.Exercise_Session;
import com.example.elderexserver.data.exercise.Exercise_Session_Detail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import security.UserPrincipal;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class WebSocketService {

    private final ClassificationService classificationService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentHashMap<String, OngoingSession> sessionCounts;

    private final PatientRoutineService patientRoutineService;
    private final ExerciseSessionService exerciseSessionService;

    public WebSocketService(ClassificationService classificationService, SimpMessagingTemplate messagingTemplate, PatientRoutineService patientRoutineService, ExerciseSessionService exerciseSessionService) {
        this.classificationService = classificationService;
        this.messagingTemplate = messagingTemplate;
        this.patientRoutineService = patientRoutineService;
        this.exerciseSessionService = exerciseSessionService;
        this.sessionCounts = new ConcurrentHashMap<>();
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
            sendFinalResult(principal, sessionId);
            return;
        }

        log.debug("Processing session {} for user {} with {} features",
                sessionId,
                principal.getName(),
                event.getData().getFeatures().size());

        FeaturesResponse response = classificationService.classify(event.getData());

        sessionCounts.putIfAbsent(sessionId, createNewSession(principal));
        OngoingSession ongoingSession = sessionCounts.get(sessionId);

        ongoingSession.incrementExerciseCount(response.getExercise_id());

        Exercise_Session_Detail detail = new Exercise_Session_Detail();
        detail.setExercise_id(response.getExercise_id());
        detail.setReps(1);
        detail.setStart_time(event.getData().getStartTime());
        detail.setEnd_time(event.getData().getEndTime());

        ongoingSession.addSessionDetail(detail);

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

    private void sendFinalResult(Principal principal, String sessionId) {
        OngoingSession ongoingSession = sessionCounts.get(sessionId);

        if (ongoingSession == null) {
            log.warn("No session data found for sessionId: {}. Sending empty result.", sessionId);
            SessionResultResponse emptyResponse = new SessionResultResponse(
                    "session_result",
                    System.currentTimeMillis(),
                    new ArrayList<>()
            );
            sendResultToClient(principal, emptyResponse);
            return;
        }

        ConcurrentHashMap<Integer, Integer> counts = ongoingSession.getCount();

        List<SessionResultResponse.SessionExercis> exercises = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            exercises.add(new SessionResultResponse.SessionExercis(
                    String.valueOf(entry.getKey()),
                    entry.getValue()
            ));
        }

        SessionResultResponse response = new SessionResultResponse("session_result", System.currentTimeMillis(), exercises);

        sendResultToClient(principal, response);

        UserPrincipal user = asUserPrincipal(principal);
        log.info("📤 Sent final result: UserID={}, SessionID={}, Exercises={}, TotalReps={}",
                user.getUserId(),
                user.getSessionId(),
                exercises.size(),
                counts.values().stream().mapToInt(Integer::intValue).sum());

        try {
            Exercise_Session session = ongoingSession.getSession();
            session.setEnd_time(LocalDateTime.now());
            exerciseSessionService.save(session);
            log.info("💾 Saved session to database: SessionID={}", sessionId);
        } catch (Exception e) {
            log.error("Failed to save session {}: {}", sessionId, e.getMessage(), e);
        }

        sessionCounts.remove(sessionId);
        log.debug("Cleaned up session data for: {}", sessionId);
    }

    private OngoingSession createNewSession(Principal principal) {
        OngoingSession ongoingSession = new OngoingSession();
        ongoingSession.setCount(new ConcurrentHashMap<>());

        Exercise_Session session = new Exercise_Session();
        if (principal instanceof UserPrincipal userPrincipal) {
            Integer patientId = userPrincipal.getUserId();
            session.setPatientRoutine(patientRoutineService.getCurrentPatientRoutineByPatientId(patientId));
        } else {
            throw new IllegalStateException("Invalid principal type. Expected UserPrincipal.");
        }
        session.setStart_time(LocalDateTime.now());
        session.setExercise_session_details(new ArrayList<>());

        ongoingSession.setSession(session);

        return ongoingSession;
    }
}