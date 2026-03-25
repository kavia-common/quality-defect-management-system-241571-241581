package com.example.springbootbackend.api;

import com.example.springbootbackend.api.dto.AuthDtos;
import com.example.springbootbackend.security.JwtService;
import com.example.springbootbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // PUBLIC_INTERFACE
    @PostMapping("/login")
    @Operation(
            summary = "Login with username/password",
            description = "Returns a JWT access token if credentials are valid."
    )
    public ResponseEntity<AuthDtos.TokenResponse> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        Set<String> roles = userService.authenticateAndGetRoles(req.username(), req.password());
        String token = jwtService.generateAccessToken(req.username(), roles);
        return ResponseEntity.ok(new AuthDtos.TokenResponse(token, "Bearer", roles));
    }
}
