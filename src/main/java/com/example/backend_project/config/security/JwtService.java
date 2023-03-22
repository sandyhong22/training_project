package com.example.backend_project.config.security;

import com.example.backend_project.expection.AuthenticationException;
import com.example.backend_project.expection.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.backend_project.enums.ErrorCode.*;

@Component
@Slf4j
public class JwtService {
    
    @Value("${jwt.secretKey}")
    private String secretKey;
    
    @Value("${jwt.expireHours}")
    private Integer expireHours;
    
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        
        Date expireDate =
                new Date(System.currentTimeMillis() + expireHours * 60 * 60 * 1000);
        
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException(INVALID_JWT_TOKEN, "Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException(EXPIRED_JWT_TOKEN, "Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenException(UNSUPPORTED_JWT_TOKEN, "Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException(INVALID_JWT_COMPACT_HANDLER, "JWT token compact of handler is invalid");
        } catch (Exception e) {
            throw new InvalidTokenException(UNEXPECTED_JWT_TOKEN_ERROR, e.getMessage());
        }
    }
    
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getAllClaims(token).get("sub").toString();
        return (username.equals(userDetails.getUsername()));
        
    }
    
    public String isSubValid(String token) {
        if (getAllClaims(token).get("sub") == null) {
            throw new AuthenticationException("JWT token don't have sub");
        } else {
            return getAllClaims(token).get("sub").toString();
        }
    }
    
}
