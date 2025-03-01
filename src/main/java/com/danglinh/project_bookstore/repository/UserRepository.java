package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    public User findByUsername(String username);

    public Optional<User> findByEmail(String email);

    public User findByUsernameAndRefreshToken(String username, String refreshToken);

    public User findByUsernameAndActivateCode(String username, String activateCode);
}
