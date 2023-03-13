package com.example.backend_project.controller.user.service;

import com.example.backend_project.entity.User;
import com.example.backend_project.expection.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JWTService {
    private static final String KEY = "eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoic3NzIiwiZW1haWwiOiJ0ZXN0dCIsInVzZXJuYW1lIjoic2FuZHl5eSIsImV4cCI6MT";

    public Claims decodeToken(HttpServletRequest request) throws AuthException {
        String token =subJwtToken(request);
        validateToken(token);
        return getAllClaims(token);


    }
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());


        Date expireDate =
                //設定30min過期
                new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expireDate)
                .signWith(getKey(),SignatureAlgorithm.HS512)
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(String token) throws AuthException {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new AuthException("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            throw new AuthException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new AuthException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new AuthException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new AuthException("JWT token compact of handler are invalid");
        }
    }


    public String subJwtToken(HttpServletRequest request) throws AuthException {
        final String requestTokenHeader = request.getHeader("Authorization");


        if (StringUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer")) {
            return requestTokenHeader.substring(7);
        } else {
            log.error("URL does not has Bearer header {}", request.getRequestURL());
            throw new AuthenticationException("INVALID_HEADER");
        }
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}
