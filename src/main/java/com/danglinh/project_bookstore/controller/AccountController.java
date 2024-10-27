package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.entity.User;
import com.danglinh.project_bookstore.security.Endpoints;
import com.danglinh.project_bookstore.security.LoginRequest;
import com.danglinh.project_bookstore.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = Endpoints.front_end_port)
public class AccountController {
    @Autowired
    private AccountServiceImpl accountService;


    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Validated @RequestBody User user) {
        ResponseEntity<?> response = accountService.userRegister(user);
        return response;
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam String email, @RequestParam String activateCode) {
        ResponseEntity<?> response = accountService.activateAccount(email, activateCode);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody LoginRequest loginRequest) {
        ResponseEntity<?> response = accountService.userLogin(loginRequest);
        return response;
    }

//    using session
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        return ResponseEntity.ok("Logged out successfully");
//    }
}
