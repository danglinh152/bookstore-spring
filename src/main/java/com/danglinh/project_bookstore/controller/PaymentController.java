package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Payment;
import com.danglinh.project_bookstore.service.PaymentService;
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
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments")
    @ApiMessage("Fetch All Payments")
    public ResponseEntity<ResponsePaginationDTO> getAllPayments(
            @Filter Specification<Payment> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(paymentService.findAllPayments(spec, pageable));
    }

    @GetMapping("/payments/{id}")
    @ApiMessage("Fetch A Payment with Id")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (paymentService.findPaymentById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentService.findPaymentById(id));
    }

    @PostMapping("/payments")
    @ApiMessage("Create A Payment")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        if (paymentService.addPayment(payment) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @PutMapping("/payments")
    @ApiMessage("Update A Payment")
    public ResponseEntity<Payment> updatePayment(@Valid @RequestBody Payment payment) {
        if (paymentService.updatePayment(payment) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(payment);
    }

    @DeleteMapping("/payments/{id}")
    @ApiMessage("Delete A Payment with Id")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {
        if (paymentService.deletePayment(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Payment deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
