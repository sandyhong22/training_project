package com.example.backend_project.controller.user.dto.request;


import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterRequest {
    @Column(name = "username")
    @NotBlank
    private String username;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "name")
    @NotBlank
    private String name;
    
    @Column(name = "password")
    @NotBlank
    private String password;
}
