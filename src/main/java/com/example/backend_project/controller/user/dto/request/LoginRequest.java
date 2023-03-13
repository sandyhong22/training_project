package com.example.backend_project.controller.user.dto.request;


import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
