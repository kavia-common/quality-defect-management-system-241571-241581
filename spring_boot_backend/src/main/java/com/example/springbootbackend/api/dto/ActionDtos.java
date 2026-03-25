package com.example.springbootbackend.api.dto;

import com.example.springbootbackend.domain.ActionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDate;

public class ActionDtos {

    @Schema(name = "ActionCreateRequest")
    public record ActionCreateRequest(
            @NotBlank @Size(max = 500) String description,
            @Size(max = 128) String owner,
            LocalDate dueDate
    ) {
    }

    @Schema(name = "ActionUpdateRequest")
    public record ActionUpdateRequest(
            @NotBlank @Size(max = 500) String description,
            @Size(max = 128) String owner,
            LocalDate dueDate,
            @NotNull ActionStatus status
    ) {
    }

    @Schema(name = "ActionResponse")
    public record ActionResponse(
            Long id,
            Long defectId,
            String description,
            String owner,
            LocalDate dueDate,
            ActionStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
    }
}
