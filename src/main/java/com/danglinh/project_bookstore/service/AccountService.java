package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.entity.User;
import com.danglinh.project_bookstore.security.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    public ResponseEntity<?> userRegister(User user);
    public ResponseEntity<?> userLogin(LoginRequest loginRequest);
    public ResponseEntity<?> activateAccount(String email, String activateCode);
    public String createActivateCode();
    public void sendMailActivate(String email, String activateCode);
}
