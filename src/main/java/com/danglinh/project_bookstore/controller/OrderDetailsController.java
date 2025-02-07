package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.OrderDetails;
import com.danglinh.project_bookstore.service.OrderDetailsService;
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
public class OrderDetailsController {
    private OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/orderDetailss")
    @ApiMessage("Fetch All OrderDetailss")
    public ResponseEntity<ResponsePaginationDTO> getAllOrderDetailss(
            @Filter Specification<OrderDetails> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(orderDetailsService.findAllOrderDetails(spec, pageable));
    }

    @GetMapping("/orderDetailss/{id}")
    @ApiMessage("Fetch A OrderDetails with Id")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (orderDetailsService.findOrderDetailsById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetailsService.findOrderDetailsById(id));
    }

    @PostMapping("/orderDetailss")
    @ApiMessage("Create A OrderDetails")
    public ResponseEntity<OrderDetails> createOrderDetails(@Valid @RequestBody OrderDetails orderDetails) {
        if (orderDetailsService.addOrderDetails(orderDetails) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetails);
    }

    @PutMapping("/orderDetailss")
    @ApiMessage("Update A OrderDetails")
    public ResponseEntity<OrderDetails> updateOrderDetails(@Valid @RequestBody OrderDetails orderDetails) {
        if (orderDetailsService.updateOrderDetails(orderDetails) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
    }

    @DeleteMapping("/orderDetailss/{id}")
    @ApiMessage("Delete A OrderDetails with Id")
    public ResponseEntity<String> deleteOrderDetails(@PathVariable int id) {
        if (orderDetailsService.deleteOrderDetails(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("OrderDetails deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
