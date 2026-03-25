package com.example.springbootbackend.security;

import com.example.springbootbackend.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

/**
 * Service for JWT issuing and verification.
 */
@Service
public class JwtService {

    private final JwtProperties props;
    private final SecretKey key;

    public JwtService(JwtProperties props) {
        this.props = props;
        // HMAC key must be >= 256 bits for HS256. Keep it in env: QDMS_JWT_SECRET.
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * PUBLIC_INTERFACE
     * Creates a signed JWT access token for the given subject and roles.
     */
    public String generateAccessToken(String subject, Set<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(props.getAccessTokenMinutes() * 60);

        return Jwts.builder()
                .issuer(props.getIssuer())
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("roles", roles)
                .signWith(key)
                .compact();
    }

    /**
     * PUBLIC_INTERFACE
     * Parses and validates a JWT. Throws a runtime exception on invalid token.
     */
    public Jws<Claims> parseAndValidate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(props.getIssuer())
                .build()
                .parseSignedClaims(token);
    }
}
