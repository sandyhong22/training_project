package com.example.backend_project.expection;

public class PasswordException extends RuntimeException {
    private static final long serialVersionUID = -8674275141215412742L;
    
    public PasswordException() {
        super("This password is not match");
    }
}
