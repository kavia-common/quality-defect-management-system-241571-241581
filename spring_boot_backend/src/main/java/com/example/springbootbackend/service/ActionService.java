package com.example.springbootbackend.service;

import com.example.springbootbackend.api.dto.ActionDtos;
import com.example.springbootbackend.domain.ActionStatus;
import com.example.springbootbackend.domain.CorrectiveAction;
import com.example.springbootbackend.domain.Defect;
import com.example.springbootbackend.repo.CorrectiveActionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Corrective action tracking business logic.
 */
@Service
public class ActionService {

    private final CorrectiveActionRepository actions;
    private final DefectService defectService;

    public ActionService(CorrectiveActionRepository actions, DefectService defectService) {
        this.actions = actions;
        this.defectService = defectService;
    }

    /**
     * PUBLIC_INTERFACE
     * Lists actions for a defect.
     */
    @Transactional(readOnly = true)
    public List<CorrectiveAction> listByDefect(Long defectId) {
        defectService.getOrThrow(defectId);
        return actions.findByDefectId(defectId);
    }

    /**
     * PUBLIC_INTERFACE
     * Creates an action for a defect.
     */
    @Transactional
    public CorrectiveAction create(Long defectId, ActionDtos.ActionCreateRequest req) {
        Defect defect = defectService.getOrThrow(defectId);
        CorrectiveAction action = new CorrectiveAction();
        action.setDefect(defect);
        action.setDescription(req.description());
        action.setOwner(req.owner());
        action.setDueDate(req.dueDate());
        return actions.save(action);
    }

    /**
     * PUBLIC_INTERFACE
     * Updates an action.
     */
    @Transactional
    public CorrectiveAction update(Long actionId, ActionDtos.ActionUpdateRequest req) {
        CorrectiveAction a = actions.findById(actionId)
                .orElseThrow(() -> new EntityNotFoundException("Action not found: " + actionId));
        a.setDescription(req.description())
                .setOwner(req.owner())
                .setDueDate(req.dueDate())
                .setStatus(req.status());
        return actions.save(a);
    }

    /**
     * PUBLIC_INTERFACE
     * Deletes an action.
     */
    @Transactional
    public void delete(Long actionId) {
        if (!actions.existsById(actionId)) {
            throw new EntityNotFoundException("Action not found: " + actionId);
        }
        actions.deleteById(actionId);
    }

    /**
     * PUBLIC_INTERFACE
     * Counts overdue actions (status != DONE and dueDate < today).
     */
    @Transactional(readOnly = true)
    public long countOverdue() {
        return actions.countByStatusNotAndDueDateBefore(ActionStatus.DONE, LocalDate.now());
    }
}
