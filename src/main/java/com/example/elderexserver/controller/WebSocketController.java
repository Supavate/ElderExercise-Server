package com.example.elderexserver.controller;

import com.example.elderexserver.data.exercise.DTO.ExerciseDataEvent;
import com.example.elderexserver.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    @Autowired
    private final WebSocketService webSocketService;

    @MessageMapping("/websocket")
    public void websocketMessage(@Payload ExerciseDataEvent data, SimpMessageHeaderAccessor headerAccessor) {

        String sessionId = headerAccessor.getSessionId();

        if (sessionId == null) {
            log.warn("Received message with null sessionId");
            return;
        }

        log.info("Received - SessionId: {}, Count: {}",
                sessionId,
                data.getCount());

        webSocketService.handleExerciseData(data, sessionId);
    }
}
