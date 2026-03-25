package com.example.springbootbackend.service;

import com.example.springbootbackend.api.dto.RcaDtos;
import com.example.springbootbackend.domain.Defect;
import com.example.springbootbackend.domain.RcaFiveWhys;
import com.example.springbootbackend.repo.RcaFiveWhysRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Root cause analysis service (5 Whys).
 */
@Service
public class RcaService {

    private final RcaFiveWhysRepository rcas;
    private final DefectService defectService;

    public RcaService(RcaFiveWhysRepository rcas, DefectService defectService) {
        this.rcas = rcas;
        this.defectService = defectService;
    }

    /**
     * PUBLIC_INTERFACE
     * Gets RCA by defect id, or null if none.
     */
    @Transactional(readOnly = true)
    public RcaFiveWhys getByDefectIdOrNull(Long defectId) {
        return rcas.findByDefectId(defectId).orElse(null);
    }

    /**
     * PUBLIC_INTERFACE
     * Upserts RCA for a defect.
     */
    @Transactional
    public RcaFiveWhys upsert(Long defectId, RcaDtos.RcaUpsertRequest req) {
        Defect defect = defectService.getOrThrow(defectId);

        RcaFiveWhys rca = rcas.findByDefectId(defectId).orElseGet(() -> {
            RcaFiveWhys r = new RcaFiveWhys();
            r.setDefect(defect);
            return r;
        });

        rca.setWhy1(req.why1());
        rca.setWhy2(req.why2());
        rca.setWhy3(req.why3());
        rca.setWhy4(req.why4());
        rca.setWhy5(req.why5());
        rca.setRootCause(req.rootCause());

        return rcas.save(rca);
    }

    /**
     * PUBLIC_INTERFACE
     * Deletes RCA by defect id.
     */
    @Transactional
    public void deleteByDefect(Long defectId) {
        RcaFiveWhys existing = rcas.findByDefectId(defectId).orElseThrow(() ->
                new EntityNotFoundException("RCA not found for defect: " + defectId));
        rcas.delete(existing);
    }
}
