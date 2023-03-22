package com.example.backend_project.expection;

public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 5828184887608085573L;
    
    public AuthenticationException(String msg) {
        super(msg);
    }
}
