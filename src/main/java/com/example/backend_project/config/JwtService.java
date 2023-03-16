package com.example.backend_project.config;

import com.example.backend_project.expection.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtService {
    private static final String KEY = "404E635266556A586E327235753878404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY);
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
                //set 24hr expire
                new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    public void validateToken(String token) throws AuthException {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
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
    
    public String subJwtToken(@NonNull HttpServletRequest request,
                              @NonNull HttpServletResponse response,
                              @NonNull FilterChain filterChain) throws AuthException, ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        
        filterChain.doFilter(request, response);
        
        
        if (StringUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer")) {
            return requestTokenHeader.substring(7);
        } else {
            log.error("URL does not has Bearer header {}", request.getRequestURL());
            throw new AuthenticationException("INVALID_HEADER");
        }
    }
    
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getAllClaims(token).get("sub").toString();
        return (username.equals(userDetails.getUsername()));
        
    }
    
}
