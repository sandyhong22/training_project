package com.example.backend_project.controller.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterRequest {
    
    @Schema(title = "使用者名稱")
    @Column(name = "username")
    @NotBlank
    private String username;
    
    @Schema(title = "email")
    @Column(name = "email")
    private String email;
    
    @Schema(title = "名稱")
    @Column(name = "name")
    @NotBlank
    private String name;
    
    @Schema(title = "密碼")
    @Column(name = "password")
    @NotBlank
    private String password;
}
