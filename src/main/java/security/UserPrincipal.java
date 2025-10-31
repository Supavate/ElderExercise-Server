package security;

import java.security.Principal;

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

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUniqueId() {
        return uniqueId;
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