package com.example.ratecs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "staff_ratings")
public class StaffRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Role type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @NotNull(message = "Clarity score is required")
    @Min(value = 1, message = "Clarity score must be between 1 and 10")
    @Max(value = 10, message = "Clarity score must be between 1 and 10")
    @Column(nullable = false)
    private Integer clarity;

    @NotNull(message = "Niceness score is required")
    @Min(value = 1, message = "Niceness score must be between 1 and 10")
    @Max(value = 10, message = "Niceness score must be between 1 and 10")
    @Column(nullable = false)
    private Integer niceness;

    @NotNull(message = "Knowledgeable score is required")
    @Min(value = 1, message = "Knowledgeable score must be between 1 and 10")
    @Max(value = 10, message = "Knowledgeable score must be between 1 and 10")
    @Column(nullable = false)
    private Integer knowledgeableScore;

    @Size(max = 400, message = "Comment must be at most 400 characters")
    @Column(length = 400)
    private String comment;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public double getOverallScore() {
        if (clarity == null || niceness == null || knowledgeableScore == null) {
            return 0.0;
        }
        return (clarity + niceness + knowledgeableScore) / 3.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getNiceness() {
        return niceness;
    }

    public void setNiceness(Integer niceness) {
        this.niceness = niceness;
    }

    public Integer getKnowledgeableScore() {
        return knowledgeableScore;
    }

    public void setKnowledgeableScore(Integer knowledgeableScore) {
        this.knowledgeableScore = knowledgeableScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
