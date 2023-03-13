package com.example.backend_project.expection;

public class LoginException extends RuntimeException{
    public LoginException(){
        super("This username is not found");
    }

}
