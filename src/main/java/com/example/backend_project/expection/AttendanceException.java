package com.example.backend_project.expection;

public class AttendanceException extends RuntimeException{
    
    private static final long serialVersionUID = -6935680123421223033L;
    
    public AttendanceException(String msg){
        super(msg);
    }
}
