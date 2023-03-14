package com.example.backend_project.expection;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User not found.");
    }
}
