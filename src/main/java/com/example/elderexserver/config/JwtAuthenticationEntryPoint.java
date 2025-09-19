package com.example.elderexserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.warn("Unauthorized access attempt - IP: {}, URI: {}, Method: {}, User-Agent: {}, Error: {}",
                getClientIpAddress(request),
                request.getRequestURI(),
                request.getMethod(),
                request.getHeader("User-Agent"),
                authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create structured error response
        final Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", determineErrorMessage(request, authException));
        body.put("path", request.getRequestURI());

        // Add authentication context
        String authHeader = request.getHeader("Authorization");
        Map<String, Object> authContext = new LinkedHashMap<>();
        authContext.put("tokenProvided", authHeader != null && authHeader.startsWith("Bearer "));
        authContext.put("authenticationMethod", "JWT");
        body.put("authenticationContext", authContext);

        objectMapper.writeValue(response.getOutputStream(), body);
    }

    private String determineErrorMessage(HttpServletRequest request, AuthenticationException authException) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "Authentication token is required. Please provide a valid JWT token in the Authorization header.";
        }

        String exceptionMessage = authException.getMessage();
        if (exceptionMessage != null) {
            if (exceptionMessage.contains("expired")) {
                return "Your session has expired. Please login again.";
            }
            if (exceptionMessage.contains("malformed") || exceptionMessage.contains("invalid")) {
                return "Invalid authentication token. Please login again.";
            }
            if (exceptionMessage.contains("signature")) {
                return "Authentication token signature verification failed. Please login again.";
            }
        }

        return "Authentication failed. Please verify your credentials and try again.";
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}
