package com.example.backend_project.controller;

import com.example.backend_project.config.security.JwtService;
import com.example.backend_project.dto.ResponseDto;
import com.example.backend_project.expection.InvalidTokenException;
import com.example.backend_project.expection.LoginException;
import com.example.backend_project.expection.UserNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionController {
    private static final String COMMON_ERROR_CODE = "error";
    
    @ApiResponse(
            responseCode = "400",
            description = "1. Unsupported media type\n2. Missing request parameter\n3. Broken input message\n4. Unsupported request method\n5. Other error",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            LoginException.class,
    })
    
    public final ResponseDto<Void> handleBadRequest(Exception e) {
        String message = e.getMessage();
        if (e instanceof MissingServletRequestParameterException) {
            message = ((MissingServletRequestParameterException) e).getParameterName() + " parameter is missing";
        }
        return error(message);
    }

    
    /**
     * Handle intentionally threw AuthenticationException outside security filter chain
     */
    @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ InvalidTokenException.class})
    public final ResponseDto<Void> handleUnauthenticated(AuthenticationException e) {
        return error("Unauthenticated: " + e.getMessage());
    }
    
    @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            UserNotFoundException.class,
            AuthenticationException.class,
            Exception.class
    })
    
    
    public final ResponseDto<Void> handleUnexpectedException(Exception e) {
        log.error(e.getMessage(), e);
        return error(e.getMessage());
    }
    
    private ResponseDto<Void> error(String message) {
        return new ResponseDto<>(COMMON_ERROR_CODE,  message);
    }
}
