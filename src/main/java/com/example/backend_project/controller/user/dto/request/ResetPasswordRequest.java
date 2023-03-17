package com.example.backend_project.controller.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    
    @Schema(title = "使用者名稱")
    private String username;
    
    @Schema(title = "舊密碼")
    private String oldPassword;
    
    @Schema(title = "新密碼")
    private String newPassword;
}
