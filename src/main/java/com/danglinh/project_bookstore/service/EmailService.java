package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.util.error.IdInvalidException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String from, String to, String subject, String content) throws IdInvalidException {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom(from);
        simpleMessage.setTo(to);
        simpleMessage.setSubject(subject);
        simpleMessage.setText(content);
        try {
            mailSender.send(simpleMessage);
        } catch (Exception e) {
            throw new IdInvalidException("Can not send email");
        }
    }

}
