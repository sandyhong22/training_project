package com.example.backend_project.expection;

public class UserRegisterException extends RuntimeException{
    public UserRegisterException(){
        super("this username is already registered");
    }
}
