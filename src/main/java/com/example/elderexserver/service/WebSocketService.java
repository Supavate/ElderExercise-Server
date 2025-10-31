package com.example.elderexserver.service;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.data.exercise.DTO.SessionResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import security.UserPrincipal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void handleExerciseData(ExerciseDataEvent data, Principal principal) {
        if ("session_end".equals(data.getType())) {
            int totalReps = data.getCount() != null ? data.getCount() : 0;
            sendFinalResult(principal, data);
            return;
        }

        log.info("Processing - User: {}, Count: {}",
                principal.getName(),
                data.getCount());
    }

    private SessionResultResponse processExerciseData(ExerciseDataEvent data) {
        int totalReps = data.getCount() != null ? data.getCount() : 0;

        List<SessionResultResponse.SessionExercis> exercises = new ArrayList<>();

        int remainingReps = totalReps;
        int currentCount = 1;

        while (remainingReps > 0) {
            String exerciseType = determineExerciseType(currentCount);
            int repsForThisExercise = Math.min(3, remainingReps);

            SessionResultResponse.SessionExercis exerciseResult = new SessionResultResponse.SessionExercis();
            exerciseResult.setExerciseType(exerciseType);
            exerciseResult.setRep(repsForThisExercise);

            exercises.add(exerciseResult);

            remainingReps -= repsForThisExercise;
            currentCount += repsForThisExercise;
        }

        SessionResultResponse response = new SessionResultResponse();
        response.setType("final_result");
        response.setTimestamp(System.currentTimeMillis());
        response.setExercises(exercises);

        log.info("Session Result: {} reps split into {} exercises", totalReps, exercises.size());

        return response;
    }

    private String determineExerciseType(int count) {
        int index = (count - 1) / 3;
        return switch (index) {
            case 0 -> "Squat";
            case 1 -> "Push-up";
            case 2 -> "Sit-up";
            case 3 -> "Lunge";
            default -> "Plank";
        };
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

    private void sendFinalResult(Principal principal, ExerciseDataEvent data) {
        SessionResultResponse response = processExerciseData(data);

        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/topic/exercises",
                response
        );

        if (principal instanceof UserPrincipal userPrincipal) {
            log.info("📤 Sent final result: UserID={}, SessionID={}, Exercises={}",
                    userPrincipal.getUserId(),
                    userPrincipal.getSessionId(),
                    response.getExercises().size());
        } else {
            log.info("📤 Sent final result to: {}", principal.getName());
        }
    }
}
