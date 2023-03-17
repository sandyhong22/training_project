package com.example.backend_project.controller.user.dto.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class UserRegisterResponse {
    @Schema(title = "密碼")
    private String password;
    
    @Schema(title = "使用者名稱")
    private String username;

}
