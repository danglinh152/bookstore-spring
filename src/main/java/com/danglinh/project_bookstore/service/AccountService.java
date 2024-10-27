package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.DAO.UserRepository;
import com.danglinh.project_bookstore.entity.Message;
import com.danglinh.project_bookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> userRegister(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            // Trả về JSON với thông báo lỗi "Username already exists"
            return ResponseEntity.badRequest().body(new Message("Username already exists"));
        } else if (userRepository.existsByEmail(user.getEmail())) {
            // Trả về JSON với thông báo lỗi "Email already exists"
            return ResponseEntity.badRequest().body(new Message("Email already exists"));
        } else {
            String encryptPasswd = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encryptPasswd);

            user.setActivateCode(createActivateCode());
            user.setActivate(false);

            userRepository.save(user);

            sendMailActivate(user.getEmail(), user.getActivateCode());

            // Trả về JSON với thông báo thành công "User registered successfully"
            return ResponseEntity.ok(new Message("User registered successfully"));
        }
    }

    public ResponseEntity<?> activateAccount(String email, String activateCode) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (user.getActivate()) {
            return ResponseEntity.badRequest().body(new Message("Activated"));
        }
        if (user.getActivateCode().equals(activateCode)) {
            user.setActivate(true);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(new Message("Invalid activate code"));
    }

    private String createActivateCode() {
        return UUID.randomUUID().toString();
    }

    private void sendMailActivate(String email, String activateCode) {
        String subject = "Activate Code";
        String body = "Activate here: http://localhost:3000/account/activate?email=" + email + "&activateCode=" + activateCode;
        emailService.sendEmail("storetenpm@gmail.com", email, subject, body);
    }
}
