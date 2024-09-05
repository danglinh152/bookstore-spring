package com.danglinh.project_bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user")
    private User user;

    @Column(name = "book")
    private Book book;
}
