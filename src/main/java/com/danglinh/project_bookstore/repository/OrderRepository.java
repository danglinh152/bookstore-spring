package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
