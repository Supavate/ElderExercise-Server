package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.data.exercise.DTO.FeaturesResponse;
import com.example.elderexserver.data.exercise.DTO.SessionResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import security.UserPrincipal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class WebSocketService {

    @Autowired
    private ClassificationService classificationService;

    private final SimpMessagingTemplate messagingTemplate;

    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> sessionCounts;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.sessionCounts = new ConcurrentHashMap<>();
    }

    private String getSessionId(Principal principal) {
        if (principal instanceof UserPrincipal userPrincipal) {
            return userPrincipal.getSessionId();
        }

        //should not reach here
        log.error("Principal is not UserPrincipal! Type: {}", principal.getClass().getName());
        throw new IllegalStateException("Invalid principal type. Expected UserPrincipal but got: " + principal.getClass().getName());
    }

    public void handleExerciseData(ExerciseDataEvent data, Principal principal) {
        String sessionId = getSessionId(principal);

        if ("session_end".equals(data.getType())) {
            sendFinalResult(principal, sessionId);
            return;
        }

        log.info("Processing - SessionID: {}, User: {}, Features Count: {}",
                sessionId,
                principal.getName(),
                data.getFeatures().getFeatures().size()
        );

        FeaturesResponse response = classificationService.classify(data.getFeatures());

        sessionCounts.putIfAbsent(sessionId, new ConcurrentHashMap<>());
        ConcurrentHashMap<Integer, Integer> counts = sessionCounts.get(sessionId);
        counts.merge(response.getExercise_id(), 1, Integer::sum);
    }

    public void sendResultToClient(Principal principal, SessionResultResponse result) {
        try {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/topic/exercises",
                    result
            );
            log.info("Sent result to user {}", principal.getName());

        } catch (Exception e) {
            log.error("Failed to send result: {}", e.getMessage());
        }
    }

    private void sendFinalResult(Principal principal, String sessionId) {
        ConcurrentHashMap<Integer, Integer> counts = sessionCounts.getOrDefault(sessionId, new ConcurrentHashMap<>());

        List<SessionResultResponse.SessionExercis> exercises = counts.entrySet()
                .stream()
                .map(e -> new SessionResultResponse.SessionExercis(
                        String.valueOf(e.getKey()),
                        e.getValue()
                ))
                .toList();

        SessionResultResponse response = new SessionResultResponse(
                "session_result",
                System.currentTimeMillis(),
                exercises
        );

        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/topic/exercises",
                response
        );

        if (principal instanceof UserPrincipal userPrincipal) {
            log.info("📤 Sent final result: UserID={}, SessionID={}, Exercises={}, TotalReps={}",
                    userPrincipal.getUserId(),
                    userPrincipal.getSessionId(),
                    exercises.size(),
                    counts.values().stream().mapToInt(Integer::intValue).sum());
        } else {
            log.error("Invalid principal type: {}", principal.getClass().getName());
            throw new IllegalStateException("Invalid principal type");
        }

        sessionCounts.remove(sessionId);
        log.info("🧹 Cleaned up session data for: {}", sessionId);
    }
}
