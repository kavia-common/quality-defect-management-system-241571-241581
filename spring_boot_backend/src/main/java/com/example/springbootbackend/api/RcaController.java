package com.example.springbootbackend.api;

import com.example.springbootbackend.api.dto.RcaDtos;
import com.example.springbootbackend.domain.RcaFiveWhys;
import com.example.springbootbackend.service.RcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/defects/{defectId}/rca")
@Tag(name = "RCA")
public class RcaController {

    private final RcaService rcaService;

    public RcaController(RcaService rcaService) {
        this.rcaService = rcaService;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "Get RCA", description = "Gets the 5-Whys RCA for a defect (or null if none).")
    public RcaDtos.RcaResponse get(@PathVariable Long defectId) {
        RcaFiveWhys r = rcaService.getByDefectIdOrNull(defectId);
        if (r == null) {
            return null;
        }
        return toResponse(r);
    }

    // PUBLIC_INTERFACE
    @PutMapping
    @Operation(summary = "Upsert RCA", description = "Creates or updates the 5-Whys RCA for a defect.")
    public RcaDtos.RcaResponse upsert(@PathVariable Long defectId, @Valid @RequestBody RcaDtos.RcaUpsertRequest req) {
        return toResponse(rcaService.upsert(defectId, req));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping
    @Operation(summary = "Delete RCA", description = "Deletes the RCA entry for a defect.")
    public ResponseEntity<Void> delete(@PathVariable Long defectId) {
        rcaService.deleteByDefect(defectId);
        return ResponseEntity.noContent().build();
    }

    private static RcaDtos.RcaResponse toResponse(RcaFiveWhys r) {
        return new RcaDtos.RcaResponse(
                r.getId(),
                r.getDefect().getId(),
                r.getWhy1(),
                r.getWhy2(),
                r.getWhy3(),
                r.getWhy4(),
                r.getWhy5(),
                r.getRootCause()
        );
    }
}
