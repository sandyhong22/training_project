package com.example.backend_project.expection;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -5579558911704357707L;
    
    public UserNotFoundException(){
        super("User not found.");
    }
}
