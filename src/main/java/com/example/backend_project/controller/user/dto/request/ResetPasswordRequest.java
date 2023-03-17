package com.example.backend_project.controller.user.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    
    private String username;
    
    private String oldPassword;
    
    private String newPassword;
}
