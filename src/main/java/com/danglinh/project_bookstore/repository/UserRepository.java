package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findById(int id);
}
