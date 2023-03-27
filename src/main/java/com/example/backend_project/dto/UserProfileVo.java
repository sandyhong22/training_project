package com.example.backend_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileVo {
    
    private String username;
    
    private String email;
    
    private String name;
    
    private String bearerToken;
    
    private Locale language;
}
