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

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    @Autowired
    private final WebSocketService webSocketService;

    @MessageMapping("/websocket")
    public void websocketMessage(@Payload ExerciseDataEvent data, SimpMessageHeaderAccessor headerAccessor, Principal principal) {

        if (principal == null) {
            log.warn("⚠️ Received WebSocket message from unauthenticated user");
            return;
        }

        log.info("Received message from user: {},Type: {}, Count: {}", principal.getName(), data.getType(), data.getCount());

        webSocketService.handleExerciseData(data, headerAccessor.getUser());
    }
}
