package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Order;
import com.danglinh.project_bookstore.service.OrderService;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    @ApiMessage("Fetch All Orders")
    public ResponseEntity<ResponsePaginationDTO> getAllOrders(
            @Filter Specification<Order> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.findAllOrders(spec, pageable));
    }

    @GetMapping("/orders/{id}")
    @ApiMessage("Fetch A Order with Id")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (orderService.findOrderById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PostMapping("/orders")
    @ApiMessage("Create A Order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        if (orderService.addOrder(order) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/orders")
    @ApiMessage("Update A Order")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) {
        if (orderService.updateOrder(order) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @DeleteMapping("/orders/{id}")
    @ApiMessage("Delete A Order with Id")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Order deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
