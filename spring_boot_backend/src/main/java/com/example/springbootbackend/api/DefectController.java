package com.example.springbootbackend.api;

import com.example.springbootbackend.api.dto.DefectDtos;
import com.example.springbootbackend.domain.Defect;
import com.example.springbootbackend.service.DefectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/defects")
@Tag(name = "Defects")
public class DefectController {

    private final DefectService defectService;

    public DefectController(DefectService defectService) {
        this.defectService = defectService;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List defects", description = "Returns all defects.")
    public List<DefectDtos.DefectResponse> list() {
        return defectService.list().stream().map(DefectController::toResponse).toList();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get defect", description = "Returns a defect by id.")
    public DefectDtos.DefectResponse get(@PathVariable Long id) {
        return toResponse(defectService.getOrThrow(id));
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create defect", description = "Creates a new defect.")
    public DefectDtos.DefectResponse create(@Valid @RequestBody DefectDtos.DefectCreateRequest req, Authentication auth) {
        String username = auth == null ? null : auth.getName();
        return toResponse(defectService.create(req, username));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update defect", description = "Updates an existing defect.")
    public DefectDtos.DefectResponse update(@PathVariable Long id, @Valid @RequestBody DefectDtos.DefectUpdateRequest req) {
        return toResponse(defectService.update(id, req));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete defect", description = "Deletes a defect by id.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        defectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static DefectDtos.DefectResponse toResponse(Defect d) {
        return new DefectDtos.DefectResponse(
                d.getId(),
                d.getTitle(),
                d.getDescription(),
                d.getSeverity(),
                d.getStatus(),
                d.getLocation(),
                d.getCreatedBy(),
                d.getCreatedAt(),
                d.getUpdatedAt()
        );
    }
}
