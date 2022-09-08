package com.faraf.security;

import com.faraf.dto.JWTAuthResponse;
import com.faraf.exception.ValidationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;


    // generate token
    public JWTAuthResponse generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        int jwtExpirationInMs = 2 * 60 * 60 * 1000;// hour * minutes * seconds * millis
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return new JWTAuthResponse(token, String.valueOf(jwtExpirationInMs));
    }

    // get username from the token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {
        LocalDateTime now = LocalDateTime.now();

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new ValidationException("Invalid JWT token", HttpStatus.BAD_REQUEST, now);
        } catch (ExpiredJwtException ex) {
            throw new ValidationException("Expired JWT token", HttpStatus.BAD_REQUEST, now);
        } catch (UnsupportedJwtException ex) {
            throw new ValidationException("Unsupported JWT token", HttpStatus.BAD_REQUEST, now);
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("JWT claims string is empty.", HttpStatus.BAD_REQUEST, now);
        }
    }
}
