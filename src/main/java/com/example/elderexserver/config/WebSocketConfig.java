package com.example.elderexserver.config;

import com.example.elderexserver.service.WebSocketSessionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import security.JwtUtil;
import security.UserPrincipal;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;
    private final WebSocketSessionRegistry sessionRegistry;

    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor != null) {
                    String sessionId = accessor.getSessionId();

                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        return handleConnect(accessor, sessionId, message);
                    }

                    if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                        handleDisconnect(accessor, sessionId);
                    }
                }

                return message;
            }
        });
    }

    private Message<?> handleConnect(StompHeaderAccessor accessor, String sessionId, Message<?> message) {
        try {
            String token = accessor.getFirstNativeHeader("token");

            if (token == null || token.trim().isEmpty()) {
                log.warn("WebSocket connection rejected: No token provided (SessionID={})", sessionId);
                throw new IllegalArgumentException("Authentication token is required");
            }

            if (!jwtUtil.validateToken(token)) {
                log.warn("WebSocket connection rejected: Invalid token (SessionID={})", sessionId);
                throw new IllegalArgumentException("Invalid authentication token");
            }

            // Extract user information
            Integer userId = jwtUtil.getUserIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);

            if (userId == null) {
                log.warn("WebSocket connection rejected: No userId in token (SessionID={})", sessionId);
                throw new IllegalArgumentException("Invalid user information");
            }

            // Create UserPrincipal with userId-sessionId
            UserPrincipal userPrincipal = new UserPrincipal(userId, username, sessionId);
            accessor.setUser(userPrincipal);

            // Register session
            sessionRegistry.registerSession(userId, sessionId);

            log.info("✅ WebSocket connected: UserID={}, Username={}, SessionID={}, UniqueID={}",
                    userId, username, sessionId, userPrincipal.getUniqueId());

            return message;

        } catch (Exception e) {
            log.error("❌ WebSocket authentication failed (SessionID={}): {}", sessionId, e.getMessage());
            throw new IllegalArgumentException("Authentication failed: " + e.getMessage());
        }
    }

    private void handleDisconnect(StompHeaderAccessor accessor, String sessionId) {
        try {
            if (accessor.getUser() instanceof UserPrincipal userPrincipal) {
                Integer userId = userPrincipal.getUserId();

                // Unregister session
                sessionRegistry.removeSession(userId, sessionId);

                log.info("❌ WebSocket disconnected: UserID={}, SessionID={}", userId, sessionId);
            } else {
                // Fallback: try to remove by sessionId
                sessionRegistry.removeSessionById(sessionId);
                log.info("❌ WebSocket disconnected: SessionID={}", sessionId);
            }
        } catch (Exception e) {
            log.error("Error handling disconnect (SessionID={}): {}", sessionId, e.getMessage());
        }
    }
}
