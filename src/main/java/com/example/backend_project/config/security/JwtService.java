package com.example.backend_project.config.security;

import com.example.backend_project.dto.UserDto;
import com.example.backend_project.entity.User;
import com.example.backend_project.expection.AuthenticationException;
import com.example.backend_project.expection.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.security.auth.message.AuthException;
import java.security.Key;
import java.util.*;

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
    
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    
    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    ) {
        
        Date expireDate =
                new Date(System.currentTimeMillis() + expireHours * 60 * 60 * 1000);
        
        extraClaims.put("email", userDetails.getEmail());
        extraClaims.put("name", userDetails.getName());
        
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    public Authentication validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            UserDto user = toUser(token);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
            return new TokenAuthenticatedUser(user, token, authorities);
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
    
    public UserDto toUser(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        UserDto userDto = new UserDto();
        userDto.setName(claims.get("name").toString());
        userDto.setEmail(claims.get("email").toString());
        userDto.setUsername(claims.get("sub").toString());
        return userDto;
    }
    
    public List<SimpleGrantedAuthority> getAuthorities(UserDto user) {
        // TODO: get authorities of the given user when needed in the future
        return new ArrayList<>();
    }
    
    public Claims getAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
            
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e.getMessage());
        }
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
    
    @EqualsAndHashCode(callSuper = true)
    private static class TokenAuthenticatedUser extends AbstractAuthenticationToken {
        
        private static final long serialVersionUID = -1887189041806242022L;
        
        private final UserDto principal;
        
        private String credentials;
        
        private TokenAuthenticatedUser(UserDto principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.principal = principal;
            this.credentials = credentials;
            super.setAuthenticated(true); // must use super, as we override
        }
        
        @Override
        public String getCredentials() {
            return this.credentials;
        }
        
        @Override
        public UserDto getPrincipal() {
            return this.principal;
        }
        
        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            Assert.isTrue(!isAuthenticated,
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
            super.setAuthenticated(false);
        }
        
        @Override
        public void eraseCredentials() {
            super.eraseCredentials();
            this.credentials = null;
        }
    }
}
