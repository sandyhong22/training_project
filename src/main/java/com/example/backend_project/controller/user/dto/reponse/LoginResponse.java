package com.example.backend_project.controller.user.dto.reponse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private String token;

}
