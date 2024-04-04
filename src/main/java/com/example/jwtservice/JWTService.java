package com.example.jwtservice;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import io.smallrye.jwt.build.JwtException;
import org.eclipse.microprofile.jwt.Claims;
import io.smallrye.jwt.build.Jwt;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;

public class JWTService {
    private static final long ACCESS_TOKEN_EXPIRATION_TIME_MINUTES = 5;

    private static final long REFRESH_TOKEN_EXPIRATION_TIME_HOURS = 24;

    public static String generateAccessToken(UUID userId, HashSet<String> groups) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ACCESS_TOKEN_EXPIRATION_TIME_MINUTES * 60); // Expiration time

        return Jwt.issuer("http://uwlify.com/issuer")
                .upn(userId.toString())
                .groups(groups)
                .expiresAt(Date.from(exp).toInstant())
                .sign();
    }

    public static String generateRefreshToken(UUID userId, HashSet<String> groups) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(REFRESH_TOKEN_EXPIRATION_TIME_HOURS * 3600); // Expiration time

        return Jwt.issuer("http://uwlify.com/issuer")
                .upn(userId.toString())
                .groups(groups)
                .expiresAt(Date.from(exp).toInstant())
                .sign();
    }

    // generate verifyRefreshToken method
    public static UUID verifyRefreshToken(String token) {
        try {
            JwtClaims claims = JwtClaims.parse(token);
            return UUID.fromString((String) claims.getClaimValue(Claims.upn.name()));
        } catch (JwtException | InvalidJwtException e) {
            // Handle JwtException
            e.printStackTrace();
            return null; // Or throw your custom exception
        }
    }
}
