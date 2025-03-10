package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Cart;
import com.danglinh.project_bookstore.domain.entity.Order;
import com.danglinh.project_bookstore.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {
    public Optional<Cart> findByUser(User user);
}
