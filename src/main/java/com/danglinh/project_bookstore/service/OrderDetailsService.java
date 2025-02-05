package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.entity.OrderDetails;
import com.danglinh.project_bookstore.repository.OrderDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {
    private OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public OrderDetails findOrderDetailsById(int id) {
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
        if (orderDetails.isPresent()) {
            return orderDetails.get();
        }
        return null;
    }

    public List<OrderDetails> findAllOrderDetails() {
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        if (orderDetails.isEmpty()) {
            return null;
        }
        return orderDetails;
    }

    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetails updateOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public Boolean deleteOrderDetails(int id) {
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
        if (orderDetails.isPresent()) {
            orderDetailsRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
