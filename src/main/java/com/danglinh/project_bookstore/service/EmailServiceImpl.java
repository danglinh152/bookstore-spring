package com.danglinh.project_bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String from, String to, String subject, String body) {
        // MIME Mail Message
        // Simple Mail Message
         SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom(from);
         message.setTo(to);
         message.setSubject(subject);
         message.setText(body);
         mailSender.send(message);
    }
}
