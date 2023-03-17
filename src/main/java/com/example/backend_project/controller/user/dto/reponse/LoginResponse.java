package com.example.backend_project.controller.user.dto.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    @Schema(title = "JWT", description = "Jwt Token")
    private String token;

}
