package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.OrderDetails;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.OrderDetailsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        return orderDetails.orElse(null);
    }

    public ResponsePaginationDTO findAllOrderDetails(Specification<OrderDetails> spec, Pageable pageable) {
        Page<OrderDetails> pageOrderDetails = orderDetailsRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageOrderDetails.getTotalElements());
        meta.setTotalPages(pageOrderDetails.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageOrderDetails.getContent());

        return responsePaginationDTO;
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
