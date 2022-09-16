package com.faraf.security;

import com.faraf.dto.response.JWTAuthResponse;
import com.faraf.exception.AuthException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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
    public boolean validateToken(String token) throws AuthException {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new AuthException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new AuthException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new AuthException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new AuthException("JWT claims string is empty.");
        }
    }
}
