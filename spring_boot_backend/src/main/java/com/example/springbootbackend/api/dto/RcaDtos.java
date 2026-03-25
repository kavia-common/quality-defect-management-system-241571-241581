package com.example.springbootbackend.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public class RcaDtos {

    @Schema(name = "RcaUpsertRequest")
    public record RcaUpsertRequest(
            @Size(max = 2000) String why1,
            @Size(max = 2000) String why2,
            @Size(max = 2000) String why3,
            @Size(max = 2000) String why4,
            @Size(max = 2000) String why5,
            @Size(max = 2000) String rootCause
    ) {
    }

    @Schema(name = "RcaResponse")
    public record RcaResponse(
            Long id,
            Long defectId,
            String why1,
            String why2,
            String why3,
            String why4,
            String why5,
            String rootCause
    ) {
    }
}
