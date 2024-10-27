package com.danglinh.project_bookstore.service;

public interface EmailService {
    public void sendEmail(String from, String to, String subject, String body);
}
