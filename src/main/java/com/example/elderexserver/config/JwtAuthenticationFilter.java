package com.example.elderexserver.config;

import com.example.elderexserver.service.PatientUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import security.JwtUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // Constants
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    // Public endpoints that should skip JWT processing
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
            "/auth/**",
            "/public/**",
            "/actuator/health",
            "/error",
            "/h2-console/",
            "/swagger-ui/",
            "/v3/api-docs/"
    );

    private final JwtUtil jwtUtil;
    private final PatientUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        final String method = request.getMethod();

        try {
            // Extract JWT token from request
            String jwt = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                logger.debug("JWT token found in request: {} {}", method, requestURI);

                // Validate token
                if (jwtUtil.validateToken(jwt)) {
                    // Extract username from token
                    String username = jwtUtil.getUsernameFromToken(jwt);
                    logger.debug("Valid JWT token for user: {}", username);

                    // Load user details
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (userDetails != null && userDetails.isEnabled()) {
                        // Create authentication token
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                        // Set authentication details
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        // Set authentication in security context
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        logger.debug("Successfully authenticated user: {} for {} {}",
                                username, method, requestURI);
                    } else {
                        logger.warn("User details not found or disabled for user: {}", username);
                    }
                } else {
                    logger.debug("Invalid JWT token for request: {} {}", method, requestURI);
                }
            } else {
                logger.debug("No JWT token found in request: {} {}", method, requestURI);
            }

        } catch (Exception ex) {
            logger.error("Cannot set user authentication in security context for {} {}: {}",
                    method, requestURI, ex.getMessage());

            // Clear security context on error
            SecurityContextHolder.clearContext();
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from Authorization header
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(authorizationHeader) &&
                authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Skip JWT processing for public endpoints
        boolean isPublicEndpoint = PUBLIC_ENDPOINTS.stream()
                .anyMatch(path::startsWith);

        if (isPublicEndpoint) {
            logger.debug("Skipping JWT filter for public endpoint: {} {}", method, path);
            return true;
        }

        if ("OPTIONS".equalsIgnoreCase(method)) {
            logger.debug("Skipping JWT filter for OPTIONS request: {}", path);
            return true;
        }

        return false;
    }
}
