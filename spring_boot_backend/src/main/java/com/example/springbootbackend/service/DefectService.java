package com.example.springbootbackend.service;

import com.example.springbootbackend.api.dto.DefectDtos;
import com.example.springbootbackend.domain.Defect;
import com.example.springbootbackend.repo.DefectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Defect CRUD business logic.
 */
@Service
public class DefectService {

    private final DefectRepository defects;

    public DefectService(DefectRepository defects) {
        this.defects = defects;
    }

    /**
     * PUBLIC_INTERFACE
     * Creates a defect.
     */
    @Transactional
    public Defect create(DefectDtos.DefectCreateRequest req, String createdBy) {
        Defect d = new Defect()
                .setTitle(req.title())
                .setDescription(req.description())
                .setSeverity(req.severity())
                .setLocation(req.location())
                .setCreatedBy(createdBy);
        return defects.save(d);
    }

    /**
     * PUBLIC_INTERFACE
     * Returns all defects.
     */
    @Transactional(readOnly = true)
    public List<Defect> list() {
        return defects.findAll();
    }

    /**
     * PUBLIC_INTERFACE
     * Gets a defect by id.
     */
    @Transactional(readOnly = true)
    public Defect getOrThrow(Long id) {
        return defects.findById(id).orElseThrow(() -> new EntityNotFoundException("Defect not found: " + id));
    }

    /**
     * PUBLIC_INTERFACE
     * Updates a defect.
     */
    @Transactional
    public Defect update(Long id, DefectDtos.DefectUpdateRequest req) {
        Defect d = getOrThrow(id);
        d.setTitle(req.title())
                .setDescription(req.description())
                .setSeverity(req.severity())
                .setStatus(req.status())
                .setLocation(req.location());
        return defects.save(d);
    }

    /**
     * PUBLIC_INTERFACE
     * Deletes a defect.
     */
    @Transactional
    public void delete(Long id) {
        if (!defects.existsById(id)) {
            throw new EntityNotFoundException("Defect not found: " + id);
        }
        defects.deleteById(id);
    }
}
