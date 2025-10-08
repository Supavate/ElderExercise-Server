package com.example.elderexserver.controller;

import com.example.elderexserver.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/websocket")
public class WebSocketRestController {

    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketRestController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @PostMapping("/send/{sessionId}")
    public ResponseEntity<String> sendToSession(@PathVariable String sessionId, @RequestBody String message) {
        webSocketService.sendMessage(sessionId, message);
        return ResponseEntity.ok("Message sent to session: " + sessionId);
    }

    @PostMapping("/broadcast")
    public ResponseEntity<String> broadcast(@RequestBody String message) {
        webSocketService.broadcastMessage(message);
        return ResponseEntity.ok("Message broadcasted");
    }
}
