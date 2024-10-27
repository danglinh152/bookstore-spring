package com.danglinh.project_bookstore.service;

import com.danglinh.project_bookstore.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findByUsername(String username);
}
