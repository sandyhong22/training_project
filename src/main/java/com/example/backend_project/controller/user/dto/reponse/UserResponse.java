package com.example.backend_project.controller.user.dto.reponse;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResponse {
    @Schema(title = "名字")
    private String name;
    
    @Schema(title = "使用者名稱")
    private String username;
    
    @Schema(title = "email")
    private String email;
}
