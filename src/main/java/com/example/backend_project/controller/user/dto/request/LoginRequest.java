package com.example.backend_project.controller.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(title = "使用者名稱")
    private String username;
    
    @Schema(title = "密碼")
    private String password;
}
