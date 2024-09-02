package com.danglinh.project_bookstore.entity;

import lombok.Data;

@Data
public class Feedback {
    private long feedbackId;
    private String feedback;
    private Book book;
    private float rate;
    private User user;
}
