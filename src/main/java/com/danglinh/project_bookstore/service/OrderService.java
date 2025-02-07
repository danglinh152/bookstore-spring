package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Order;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findOrderById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public ResponsePaginationDTO findAllOrders(Specification<Order> spec, Pageable pageable) {
        Page<Order> pageOrder = orderRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageOrder.getTotalElements());
        meta.setTotalPages(pageOrder.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageOrder.getContent());

        return responsePaginationDTO;
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public Boolean deleteOrder(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
