package com.example.backend_project.dto;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Value //for immutable class
public class ResponseDto<T> implements Serializable {
    
    
    private static final long serialVersionUID = 7670592515491406895L;
    Status status;
    T data;
    
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(String.valueOf(HttpStatus.OK.value()), data);
    }
    
    public static <T> ResponseDto<T> fail(String code,String message) {
        return new ResponseDto<>(code,message);
    }
    
    public ResponseDto(String code, String message) {
        this.status = new Status(code, message);
        this.data = null;
    }
    
    private ResponseDto(String code, T data) {
        this.status = new Status(code, null);
        this.data = data;
    }
    
    @Value
    public static class Status implements Serializable {
        private static final long serialVersionUID = 1440057195675543637L;
        
        String code;
        String message;
    }
}
