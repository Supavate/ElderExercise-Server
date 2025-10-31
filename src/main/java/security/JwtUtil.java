package security;

import com.example.elderexserver.data.patient.Patient;
import com.example.elderexserver.data.staff.Staff;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value(value = "${jwt.secret}")
    private String jwtSecret;

    @Value(value = "${jwt.expiration}")
    private Long jwtExpirationMs;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Add custom claims if needed
        claims.put("authorities", userDetails.getAuthorities());

        return createToken(claims, userDetails.getUsername());
    }

    public String generatePatientToken(Patient patient) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("patientId", patient.getId());
        claims.put("citizenId", patient.getCitizenId());
        claims.put("role", "PATIENT");
        claims.put("firstName", patient.getFirstName());
        claims.put("lastName", patient.getLastName());

        String subject = patient.getEmail();
        return createToken(claims, subject);
    }

    public String generateStaffToken(Staff staff) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("staffId", staff.getId());
        claims.put("role", "STAFF");
        claims.put("firstName", staff.getFirst_Name());
        claims.put("lastName", staff.getLast_Name());

        String subject = staff.getEmail();
        return createToken(claims, subject);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();

            logger.debug("JWT token generated for user: {}", subject);
            return token;

        } catch (Exception e) {
            logger.error("Error generating JWT token for user: {}", subject, e);
            throw new RuntimeException("Could not generate JWT token", e);
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("JWT token is malformed: {}", e.getMessage());
            throw e;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            logger.error("JWT signature validation failed: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("JWT token is invalid: {}", e.getMessage());
            throw e;
        }
    }

    private Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            logger.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return !isTokenExpired(token);

        } catch (ExpiredJwtException e) {
            logger.warn("JWT token is expired");
            return false;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported");
            return false;
        } catch (MalformedJwtException e) {
            logger.error("JWT token is malformed");
            return false;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            logger.error("JWT signature validation failed");
            return false;
        } catch (IllegalArgumentException e) {
            logger.error("JWT token is invalid");
            return false;
        } catch (Exception e) {
            logger.error("JWT token validation error: {}", e.getMessage());
            return false;
        }
    }

    public Integer getPatientIdFromToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return (Integer) claims.get("patientId");
        } catch (Exception e) {
            logger.error("Error extracting patient ID from token: {}", e.getMessage());
            return null;
        }
    }

    public String getCitizenIdFromToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return (String) claims.get("citizenId");
        } catch (Exception e) {
            logger.error("Error extracting citizen ID from token: {}", e.getMessage());
            return null;
        }
    }

    public Integer getStaffIdFromToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return (Integer) claims.get("staffId");
        } catch (Exception e) {
            logger.error("Error extracting staff ID from token: {}", e.getMessage());
            return null;
        }
    }

    public Map<String, Object> getJwtInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("algorithm", "HS256");
        info.put("expirationMs", jwtExpirationMs);
        info.put("expirationHours", jwtExpirationMs / (1000 * 60 * 60));
        info.put("issuer", "elderex-auth-service");
        return info;
    }

    public Long getTokenExpiryTime(String token) {
        return jwtExpirationMs;
    }

    public Authentication getAuthentication(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);

            String username = claims.getSubject();
            String role = (String) claims.get("role");

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (role != null) {
                if (!role.startsWith("ROLE_")) {
                    role = "ROLE_" + role;
                }
                authorities.add(new SimpleGrantedAuthority(role));
            }

            UserDetails userDetails = new User(
                    username,
                    "",
                    authorities
            );

            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );

        } catch (Exception e) {
            logger.error("Error getting authentication from token: {}", e.getMessage());
            return null;
        }
    }
}
