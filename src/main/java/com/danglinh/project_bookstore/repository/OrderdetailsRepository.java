package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Orderdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "order-details")
public interface OrderdetailsRepository extends JpaRepository<Orderdetails, Long> {

}
