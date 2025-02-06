package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
