package com.example.elderexserver.controller;

import com.example.elderexserver.data.webSocket.ExerciseDataEvent;
import com.example.elderexserver.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;

    @MessageMapping("/websocket")
    public void websocketMessage(@Payload ExerciseDataEvent data, SimpMessageHeaderAccessor headerAccessor, Principal principal) {

        if (data == null) {
            log.warn("Received null data from user: {}",
                    principal != null ? principal.getName() : "unknown");
            return;
        }

        if (principal == null) {
            log.error("Received message from unauthenticated user - this should not happen!");
            return;
        }

        log.info("Received message from user: {},Type: {}, Features Count: {}",
                principal.getName(),
                data.getType(),
                data.getData() != null ? data.getData().getFeatures().size() : 0);

        try {
            webSocketService.handleExerciseData(data, principal);
        } catch (Exception e) {
            log.error("Error processing exercise data for user {}: {}", principal.getName(), e.getMessage(), e);
        }

    }
}
