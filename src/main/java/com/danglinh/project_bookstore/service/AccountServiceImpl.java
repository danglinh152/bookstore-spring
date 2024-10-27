package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.DAO.UserRepository;
import com.danglinh.project_bookstore.entity.Message;
import com.danglinh.project_bookstore.entity.User;
import com.danglinh.project_bookstore.security.JwtResponse;
import com.danglinh.project_bookstore.security.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
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

    @Override
    public ResponseEntity<?> userLogin(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                final String jwt = jwtService.generateJwtToken(authentication.getName());
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new Message("Invalid username or password"));
        }
        return ResponseEntity.badRequest().body(new Message("Fail to Authenticate"));
    }

    @Override
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

    @Override
    public String createActivateCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void sendMailActivate(String email, String activateCode) {
        String subject = "Activate Code";
        String body = "Activate here: http://localhost:3000/account/activate?email=" + email + "&activateCode=" + activateCode;
        emailService.sendEmail("storetenpm@gmail.com", email, subject, body);
    }
}
