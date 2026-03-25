package com.example.springbootbackend.repo;

import com.example.springbootbackend.domain.Defect;
import com.example.springbootbackend.domain.DefectSeverity;
import com.example.springbootbackend.domain.DefectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * JPA repository for defects.
 */
public interface DefectRepository extends JpaRepository<Defect, Long> {
    long countByStatus(DefectStatus status);

    long countBySeverity(DefectSeverity severity);

    List<Defect> findByCreatedAtBetween(Instant start, Instant end);
}
