package com.example.springbootbackend.api.dto;

import com.example.springbootbackend.domain.DefectSeverity;
import com.example.springbootbackend.domain.DefectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class DefectDtos {

    @Schema(name = "DefectCreateRequest")
    public record DefectCreateRequest(
            @Schema(example = "Scratch on housing")
            @NotBlank @Size(max = 200) String title,

            @Schema(example = "Found during final inspection on line A.")
            @NotBlank @Size(max = 4000) String description,

            @NotNull DefectSeverity severity,

            @Schema(example = "Line A")
            @Size(max = 128) String location
    ) {
    }

    @Schema(name = "DefectUpdateRequest")
    public record DefectUpdateRequest(
            @NotBlank @Size(max = 200) String title,
            @NotBlank @Size(max = 4000) String description,
            @NotNull DefectSeverity severity,
            @NotNull DefectStatus status,
            @Size(max = 128) String location
    ) {
    }

    @Schema(name = "DefectResponse")
    public record DefectResponse(
            Long id,
            String title,
            String description,
            DefectSeverity severity,
            DefectStatus status,
            String location,
            String createdBy,
            Instant createdAt,
            Instant updatedAt
    ) {
    }
}
