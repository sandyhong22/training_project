package com.example.backend_project.expection;

import com.example.backend_project.enums.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.core.AuthenticationException;
@EqualsAndHashCode(callSuper = true)
@Value
public class InvalidTokenException extends AuthenticationException {
    private static final long serialVersionUID = 5345332838026890619L;
    ErrorCode errorCode;
    String message;
    
    public InvalidTokenException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
