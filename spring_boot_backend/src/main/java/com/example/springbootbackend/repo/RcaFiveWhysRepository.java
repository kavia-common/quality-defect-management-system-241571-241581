package com.example.springbootbackend.repo;

import com.example.springbootbackend.domain.RcaFiveWhys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA repository for 5-Whys RCA entries.
 */
public interface RcaFiveWhysRepository extends JpaRepository<RcaFiveWhys, Long> {
    Optional<RcaFiveWhys> findByDefectId(Long defectId);
}
