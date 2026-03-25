package com.example.springbootbackend.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class AuthDtos {

    @Schema(name = "LoginRequest")
    public record LoginRequest(
            @Schema(description = "Username", example = "admin")
            @NotBlank @Size(max = 128) String username,

            @Schema(description = "Password", example = "admin123")
            @NotBlank @Size(min = 6, max = 128) String password
    ) {
    }

    @Schema(name = "TokenResponse")
    public record TokenResponse(
            @Schema(description = "JWT access token") String accessToken,
            @Schema(description = "Token type", example = "Bearer") String tokenType,
            @Schema(description = "Granted roles") Set<String> roles
    ) {
    }
}
