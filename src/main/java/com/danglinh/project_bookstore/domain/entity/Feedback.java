package com.danglinh.project_bookstore.domain.entity;

import com.danglinh.project_bookstore.util.security.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackId;

    @Column(name = "feedback", columnDefinition = "text")
    private String feedback;

    @Column(name = "rate")
    private float rate;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"listOfFeedback", "listOfOrderdetails", "listOfGenre", "listOfImage", "listOfFavorite"})
    private Book book;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"listOfFeedback", "listOfFavorite", "listOfOrder", "role"})
    private User user;

    @PrePersist
    public void beforeCreate() {
        this.createdAt = Instant.now();
        if (SecurityUtil.getCurrentUser().isEmpty()) {
            this.createdBy = "unknown";
        } else {
            this.createdBy = SecurityUtil.getCurrentUser().get();
        }
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = Instant.now();
        if (SecurityUtil.getCurrentUser().isEmpty()) {
            this.updatedBy = "unknown";
        } else {
            this.updatedBy = SecurityUtil.getCurrentUser().get();
        }
    }
}
