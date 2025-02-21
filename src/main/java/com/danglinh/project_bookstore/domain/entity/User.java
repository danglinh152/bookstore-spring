package com.danglinh.project_bookstore.domain.entity;

import java.time.Instant;
import java.util.List;

import com.danglinh.project_bookstore.util.security.SecurityUtil;
import com.danglinh.project_bookstore.util.constant.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "avatar", columnDefinition = "LONGTEXT")
    private String avatar; //

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password", length = 512)
    private String password;

    @Column(name = "refresh_token", columnDefinition = "LONGTEXT")
    private String refreshToken;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "buying_address")
    private String buyingAddress;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "activate")
    private Boolean activate;

    @Column(name = "activate_code")
    private String activateCode;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "user")
    private List<Feedback> listOfFeedback;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Favorite> listOfFavorite;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties("listOfUser")
    private Role role;


    @OneToMany(mappedBy = "user")
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
