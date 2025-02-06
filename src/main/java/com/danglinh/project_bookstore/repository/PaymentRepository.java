package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
