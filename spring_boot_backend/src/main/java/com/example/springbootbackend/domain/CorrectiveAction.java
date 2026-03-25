package com.example.springbootbackend.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "corrective_actions")
public class CorrectiveAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "defect_id", nullable = false)
    private Defect defect;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(length = 128)
    private String owner;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private ActionStatus status = ActionStatus.OPEN;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public Defect getDefect() {
        return defect;
    }

    public void setDefect(Defect defect) {
        this.defect = defect;
    }

    public String getDescription() {
        return description;
    }

    public CorrectiveAction setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public CorrectiveAction setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public CorrectiveAction setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public ActionStatus getStatus() {
        return status;
    }

    public CorrectiveAction setStatus(ActionStatus status) {
        this.status = status;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
