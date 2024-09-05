package com.danglinh.project_bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackId;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "book")
    private Book book;

    @Column(name = "rate")
    private float rate;

    @Column(name = "user")
    private User user;
}
