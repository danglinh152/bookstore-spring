package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.entity.User;
import com.danglinh.project_bookstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Validated  @RequestBody User user){
        ResponseEntity<?> response = accountService.userRegister(user);
        return response;
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam String email, @RequestParam String activateCode){
        ResponseEntity<?> response = accountService.activateAccount(email, activateCode);
        return response;
    }
}
