package com.example.backend_project.config.security;

import com.example.backend_project.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    
    private static final Logger logger = LoggerFactory.getLogger(UnauthorizedHandler.class);
    
    private static final ObjectMapper jsonObjectMapper = new ObjectMapper();
    
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        new ResponseDto.Status(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        
        jsonObjectMapper.writeValue(response.getOutputStream(), new ResponseDto.Status(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
    }
    
}
