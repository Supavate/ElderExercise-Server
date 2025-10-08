package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.data.exercise.DTO.SessionResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void handleExerciseData(ExerciseDataEvent data, String sessionId) {
        if ("session_end".equals(data.getType())) {
            int totalReps = data.getCount() != null ? data.getCount() : 0;
            sendFinalResult(sessionId, totalReps);
            return;
        }

        log.info("Processing - SessionId: {}, Count: {}",
                sessionId,
                data.getCount());

        data.setSessionId(sessionId);
        SessionResultResponse result = processExerciseData(data);

        sendResultToClient(sessionId, result);
    }

    private SessionResultResponse processExerciseData(ExerciseDataEvent data) {
        int amount = data.getCount() != null ? data.getCount() : 0;

        SessionResultResponse response = new SessionResultResponse();
        response.setType("result");
        response.setExerciseType("Exercise");
        response.setRep(amount);
        response.setTimestamp(System.currentTimeMillis());

        log.info("Session Result: {} reps", amount);

        return response;
    }

    private String determineExerciseType(ExerciseDataEvent data) {
        int count = data.getCount();

        if (count < 3) {
            return "Squat";
        } else if (count < 6) {
            return "Push-up";
        } else if (count < 9) {
            return "Sit-up";
        } else if (count < 12) {
            return "Lunge";
        } else {
            return "Plank";
        }
    }

    public void sendResultToClient(String sessionId, SessionResultResponse result) {
        try {
            messagingTemplate.convertAndSend(
                    "/topic/exercises/" + sessionId,
                    result
            );
            log.info("Sent result to session: {}", sessionId);

        } catch (Exception e) {
            log.error("Failed to send result: {}", e.getMessage());
        }
    }

    private void sendFinalResult(String sessionId, int totalReps) {
        SessionResultResponse result = new SessionResultResponse();
        result.setType("final_result");
        result.setExerciseType("Exercise");
        result.setRep(totalReps);
        result.setTimestamp(System.currentTimeMillis());

        messagingTemplate.convertAndSend("/topic/exercises/" + sessionId, result);
        log.info("📤 Sent final result for session {}: {} reps", sessionId, totalReps);
    }

    public void sendMessage(String sessionId, String message) {
        try {
            SessionResultResponse response = new SessionResultResponse();
            response.setType("message");
            response.setExerciseType(message);
            response.setTimestamp(System.currentTimeMillis());

            messagingTemplate.convertAndSend("/topic/exercises/" + sessionId, response);

            log.info("Sent message to session {}: {}", sessionId, message);

        } catch (Exception e) {
            log.error("Failed to send message: {}", e.getMessage());
        }
    }

    public void broadcastMessage(String message) {
        try {
            SessionResultResponse response = new SessionResultResponse();
            response.setType("broadcast");
            response.setExerciseType(message);
            response.setTimestamp(System.currentTimeMillis());

            messagingTemplate.convertAndSend("/topic/exercises", response);

            log.info("Broadcasted message: {}", message);

        } catch (Exception e) {
            log.error("Failed to broadcast message: {}", e.getMessage());
        }
    }
}
