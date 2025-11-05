package com.example.elderexserver.service;

import com.example.elderexserver.Exception.ErrorResponse;
import com.example.elderexserver.data.webSocket.ExerciseDataEvent;
import com.example.elderexserver.data.webSocket.SessionResultResponse;
import com.example.elderexserver.data.webSocket.SessionUpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import security.UserPrincipal;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final WebSocketSessionService sessionService;
    private final SimpMessagingTemplate messagingTemplate;

    public void handleExerciseData(ExerciseDataEvent event, Principal principal) {
        String sessionId = getSessionId(principal);
        Integer userId = getUserId(principal);
        String userName = asUserPrincipal(principal).getName();

        if ("session_end".equals(event.getType())) {
            handleSessionEnd(principal, event.getEndTime());
            return;
        }

        try {
            SessionUpdateResult result = sessionService.updateSession(sessionId, userId, userName, event);
            log.debug("Updated counts for session {}: exerciseId={}, count={}", sessionId, result.getExerciseId(), result.getCurrentCount());
        } catch (Exception e) {
            log.error("Error processing exercise data for session {}: {}",
                    sessionId, e.getMessage(), e);
            sendErrorToClient(principal, "Failed to process exercise data");
        }
    }

    private void handleSessionEnd(Principal principal, LocalDateTime endTime) {
        String sessionId = getSessionId(principal);
        Integer userId = getUserId(principal);

        try {
            SessionResultResponse result = sessionService.finalizeSession(sessionId, endTime);
            sendResultToClient(principal, result);

            log.info("✅ Session completed: UserID={}, SessionID={}, Exercises={}",
                    userId, sessionId, result.getExercises().size());

        } catch (Exception e) {
            log.error("Error finalizing session {}: {}", sessionId, e.getMessage(), e);
            sendErrorToClient(principal, "Failed to finalize session");
        }
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

    private void sendEmptyResult(Principal principal, LocalDateTime endTime) {
        SessionResultResponse emptyResponse = new SessionResultResponse(
                "session_result",
                endTime,
                new ArrayList<>()
        );
        sendResultToClient(principal, emptyResponse);
    }

    private void sendErrorToClient(Principal principal, String errorMessage) {
        try {
            ErrorResponse error = new ErrorResponse(
                    500,
                    "Exercise Session Error",
                    errorMessage,
                    LocalDateTime.now()
            );

            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/topic/errors",
                    error
            );
        } catch (Exception e) {
            log.error("Failed to send error to user {}: {}", principal.getName(), e.getMessage(), e);
        }
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

    private Integer getUserId(Principal principal) {
        return asUserPrincipal(principal).getUserId();
    }
}