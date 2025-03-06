package com.danglinh.project_bookstore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_details")
public class CartDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartdetails_id")
    private int cartDetailsId;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonIgnoreProperties({"user", "cart", "listOfCartdetails"})
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"listOfFeedback", "listOfOrderdetails", "listOfGenre", "listOfImage", "listOfFavorite", "listOfCartdetails"})
    private Book book;
}
