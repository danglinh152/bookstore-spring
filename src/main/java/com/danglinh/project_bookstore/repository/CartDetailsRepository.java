package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer>, JpaSpecificationExecutor<CartDetails> {
    public Optional<CartDetails> findByCartAndBook(Cart cart, Book book);

}
