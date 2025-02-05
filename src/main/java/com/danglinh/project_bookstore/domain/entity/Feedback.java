package com.danglinh.project_bookstore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private long feedbackId;

    @Column(name = "feedback", columnDefinition = "text")
    private String feedback;

    @Column(name = "rate")
    private float rate;

    @ManyToOne(
            fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH}
    )
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(
            fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH}
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
