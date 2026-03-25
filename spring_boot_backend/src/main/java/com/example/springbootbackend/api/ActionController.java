package com.example.springbootbackend.api;

import com.example.springbootbackend.api.dto.ActionDtos;
import com.example.springbootbackend.domain.CorrectiveAction;
import com.example.springbootbackend.service.ActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Actions")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/api/defects/{defectId}/actions")
    @Operation(summary = "List actions for defect", description = "Lists corrective actions for a defect.")
    public List<ActionDtos.ActionResponse> list(@PathVariable Long defectId) {
        return actionService.listByDefect(defectId).stream().map(ActionController::toResponse).toList();
    }

    // PUBLIC_INTERFACE
    @PostMapping("/api/defects/{defectId}/actions")
    @Operation(summary = "Create action", description = "Creates a corrective action for a defect.")
    public ActionDtos.ActionResponse create(@PathVariable Long defectId, @Valid @RequestBody ActionDtos.ActionCreateRequest req) {
        return toResponse(actionService.create(defectId, req));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/api/actions/{actionId}")
    @Operation(summary = "Update action", description = "Updates a corrective action.")
    public ActionDtos.ActionResponse update(@PathVariable Long actionId, @Valid @RequestBody ActionDtos.ActionUpdateRequest req) {
        return toResponse(actionService.update(actionId, req));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/api/actions/{actionId}")
    @Operation(summary = "Delete action", description = "Deletes a corrective action.")
    public ResponseEntity<Void> delete(@PathVariable Long actionId) {
        actionService.delete(actionId);
        return ResponseEntity.noContent().build();
    }

    private static ActionDtos.ActionResponse toResponse(CorrectiveAction a) {
        return new ActionDtos.ActionResponse(
                a.getId(),
                a.getDefect().getId(),
                a.getDescription(),
                a.getOwner(),
                a.getDueDate(),
                a.getStatus(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
