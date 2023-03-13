package com.example.backend_project.expection;

public class PasswordException extends RuntimeException {
    public PasswordException() {
        super("This password is not match");
    }
}
