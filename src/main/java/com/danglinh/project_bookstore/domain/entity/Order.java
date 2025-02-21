package com.danglinh.project_bookstore.domain.entity;

import java.time.Instant;
import java.util.List;

import com.danglinh.project_bookstore.util.constant.Status;
import com.danglinh.project_bookstore.util.security.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "date")
    private Instant date;

    @Column(name = "buying_address")
    private String buyingAddress;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "product_cost")
    private double productCost;

    @Column(name = "payment_cost")
    private double paymentCost;

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "total")
    private double total;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties({"order", "book"})
    private List<OrderDetails> listOfOrderdetails;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"listOfFeedback", "listOfFavorite", "listOfOrder", "role"})
    private User user;

    @ManyToOne()
    @JoinColumn(name = "payment_id", nullable = false)
    @JsonIgnoreProperties("listOfOrder")
    private Payment payment;

    @ManyToOne()
    @JoinColumn(name = "delivery_id", nullable = false)
    @JsonIgnoreProperties("listOfOrder")
    private Delivery delivery;

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
