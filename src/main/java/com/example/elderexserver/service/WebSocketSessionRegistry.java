package com.example.elderexserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketSessionRegistry {

    private final Map<Integer, Set<String>> userSessions = new ConcurrentHashMap<>();
    private final Map<String, Integer> sessionToUser = new ConcurrentHashMap<>();

    public void registerSession(Integer userId, String sessionId) {
        userSessions.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet())
                .add(sessionId);

        sessionToUser.put(sessionId, userId);

        log.info("✅ Session registered: UserID={}, SessionID={}, Total sessions for user: {}",
                userId, sessionId, userSessions.get(userId).size());
    }

    public void removeSession(Integer userId, String sessionId) {
        Set<String> sessions = userSessions.get(userId);

        if (sessions != null) {
            sessions.remove(sessionId);

            if (sessions.isEmpty()) {
                userSessions.remove(userId);
                log.info("❌ Last session removed for UserID={}", userId);
            } else {
                log.info("❌ Session removed: UserID={}, SessionID={}, Remaining sessions: {}",
                        userId, sessionId, sessions.size());
            }
        }

        sessionToUser.remove(sessionId);
    }

    public void removeSessionById(String sessionId) {
        Integer userId = sessionToUser.get(sessionId);

        if (userId != null) {
            removeSession(userId, sessionId);
        } else {
            log.warn("⚠️ Attempted to remove unknown sessionId: {}", sessionId);
        }
    }

    public Set<String> getSessionsForUser(String userId) {
        return new HashSet<>(userSessions.getOrDefault(userId, Collections.emptySet()));
    }

    public int getActiveSessionCount(String userId) {
        return userSessions.getOrDefault(userId, Collections.emptySet()).size();
    }

    public boolean hasActiveSessions(String userId) {
        return userSessions.containsKey(userId) && !userSessions.get(userId).isEmpty();
    }

    public Integer getUserIdForSession(String sessionId) {
        return sessionToUser.get(sessionId);
    }

    public Set<Integer> getAllActiveUsers() {
        return new HashSet<>(userSessions.keySet());
    }

    public int getTotalActiveSessions() {
        return userSessions.values().stream()
                .mapToInt(Set::size)
                .sum();
    }

    public void clearAll() {
        userSessions.clear();
        sessionToUser.clear();
        log.warn("⚠️ All sessions cleared");
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userSessions.size());
        stats.put("totalSessions", getTotalActiveSessions());
        stats.put("activeUsers", getAllActiveUsers());

        return stats;
    }
}