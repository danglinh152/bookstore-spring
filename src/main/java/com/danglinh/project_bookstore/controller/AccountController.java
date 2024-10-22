package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.entity.User;
import com.danglinh.project_bookstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Validated  @RequestBody User user){
        ResponseEntity<?> response = accountService.userRegister(user);
        return response;
    }
}
