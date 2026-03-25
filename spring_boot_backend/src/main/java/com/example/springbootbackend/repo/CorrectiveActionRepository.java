package com.example.springbootbackend.repo;

import com.example.springbootbackend.domain.ActionStatus;
import com.example.springbootbackend.domain.CorrectiveAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA repository for corrective actions.
 */
public interface CorrectiveActionRepository extends JpaRepository<CorrectiveAction, Long> {
    List<CorrectiveAction> findByDefectId(Long defectId);

    long countByStatus(ActionStatus status);

    long countByStatusNotAndDueDateBefore(ActionStatus status, LocalDate date);
}
