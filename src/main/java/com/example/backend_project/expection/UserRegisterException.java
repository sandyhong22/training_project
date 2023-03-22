package com.example.backend_project.expection;

public class UserRegisterException extends RuntimeException {
    private static final long serialVersionUID = -1155348068326751495L;
    
    public UserRegisterException(String errorMessage) {
        super(errorMessage);
    }
    
}
