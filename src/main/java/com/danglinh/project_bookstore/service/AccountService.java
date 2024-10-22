package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.DAO.UserRepository;
import com.danglinh.project_bookstore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> userRegister(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        else if(userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        else {
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
    }
}
