package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.CartDetails;
import com.danglinh.project_bookstore.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer>, JpaSpecificationExecutor<CartDetails> {
}
