package com.example.springbootbackend.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "defects")
public class Defect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DefectSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DefectStatus status = DefectStatus.OPEN;

    @Column(length = 128)
    private String location;

    @Column(length = 128)
    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Defect setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Defect setDescription(String description) {
        this.description = description;
        return this;
    }

    public DefectSeverity getSeverity() {
        return severity;
    }

    public Defect setSeverity(DefectSeverity severity) {
        this.severity = severity;
        return this;
    }

    public DefectStatus getStatus() {
        return status;
    }

    public Defect setStatus(DefectStatus status) {
        this.status = status;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Defect setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Defect setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
