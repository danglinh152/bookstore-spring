package com.danglinh.project_bookstore.domain.entity;

import java.time.Instant;
import java.util.List;

import com.danglinh.project_bookstore.util.security.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "payment_cost")
    private double paymentCost;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    private List<Order> listOfOrder;

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
