package com.example.backend_project.controller.user.dto.request;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;

@Data
public class UserRegisterRequest {
    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "password")
    @NotNull
    private String password;
}
