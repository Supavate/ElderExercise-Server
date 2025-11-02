package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.data.exercise.DTO.FeaturesResponse;
import com.example.elderexserver.data.exercise.DTO.SessionResultResponse;
import lombok.RequiredArgsConstructor;
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

    private final ClassificationService classificationService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> sessionCounts;

    public WebSocketService(ClassificationService classificationService, SimpMessagingTemplate messagingTemplate) {
        this.classificationService = classificationService;
        this.messagingTemplate = messagingTemplate;
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

    public void handleExerciseData(ExerciseDataEvent data, Principal principal) {
        String sessionId = getSessionId(principal);

        if ("session_end".equals(data.getType())) {
            sessionCounts.computeIfAbsent(sessionId, k -> new ConcurrentHashMap<>());
            sendFinalResult(principal, sessionId);
            return;
        }

        log.debug("Processing session {} for user {} with {} features",
                sessionId,
                principal.getName(),
                data.getFeatures().getFeatures().size());

        FeaturesResponse response = classificationService.classify(data.getFeatures());

        ConcurrentHashMap<Integer, Integer> counts = sessionCounts.computeIfAbsent(sessionId, k -> new ConcurrentHashMap<>());
        counts.merge(response.getExercise_id(), 1, Integer::sum);
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
        ConcurrentHashMap<Integer, Integer> counts = sessionCounts.getOrDefault(sessionId, new ConcurrentHashMap<>());

        List<SessionResultResponse.SessionExercis> exercises = counts.entrySet().stream()
                .map(e -> new SessionResultResponse.SessionExercis(String.valueOf(e.getKey()), e.getValue()))
                .toList();

        SessionResultResponse response = new SessionResultResponse("session_result", System.currentTimeMillis(), exercises);

        sendResultToClient(principal, response);

        UserPrincipal user = asUserPrincipal(principal);
        log.info("📤 Sent final result: UserID={}, SessionID={}, Exercises={}, TotalReps={}",
                user.getUserId(),
                user.getSessionId(),
                exercises.size(),
                counts.values().stream().mapToInt(Integer::intValue).sum());

        sessionCounts.remove(sessionId);
        log.debug("Cleaned up session data for: {}", sessionId);
    }
}