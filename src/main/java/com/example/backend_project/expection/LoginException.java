package com.example.backend_project.expection;

public class LoginException extends RuntimeException{
    private static final long serialVersionUID = -8155161169992294802L;
    
    public LoginException(){
        super("This username is not found");
    }

}
