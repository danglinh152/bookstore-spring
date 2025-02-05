package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "order-details")
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
