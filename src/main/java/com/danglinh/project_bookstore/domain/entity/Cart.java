package com.danglinh.project_bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @OneToMany(mappedBy = "cart")
    @JsonIgnoreProperties({"cart", "book"})
    private List<CartDetails> listOfCartdetails;

    @OneToOne
    @JoinColumn(name = "user_id")  // Add this line to reference the user
    private User user;
}
