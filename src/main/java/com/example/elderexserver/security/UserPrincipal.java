package com.example.elderexserver.security;

import lombok.Getter;

import java.security.Principal;

@Getter
public class UserPrincipal implements Principal {
    private final String uniqueId;    // userId-sessionId
    private final Integer userId;
    private final String username;
    private final String sessionId;

    public UserPrincipal(Integer userId, String username, String sessionId) {
        this.userId = userId;
        this.username = username;
        this.sessionId = sessionId;
        this.uniqueId = userId + "-" + sessionId;
    }

    @Override
    public String getName() {
        return uniqueId;  // Returns "userId-sessionId"
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}