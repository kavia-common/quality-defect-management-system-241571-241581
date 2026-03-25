package com.example.springbootbackend.api.error;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(name = "ApiError")
public record ApiError(
        @Schema(description = "Human-readable message") String message,
        @Schema(description = "Machine-readable error code") String code,
        @Schema(description = "Timestamp") Instant timestamp,
        @Schema(description = "Field validation errors") Map<String, String> fieldErrors
) {
}
