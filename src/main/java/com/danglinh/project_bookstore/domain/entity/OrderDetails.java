package com.danglinh.project_bookstore.domain.entity;

import com.danglinh.project_bookstore.util.security.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderdetails_id")
    private int orderDetailsId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnoreProperties({"user", "order", "listOfOrderdetails"})
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"listOfFeedback", "listOfOrderdetails", "listOfGenre", "listOfImage", "listOfFavorite"})
    private Book book;

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
