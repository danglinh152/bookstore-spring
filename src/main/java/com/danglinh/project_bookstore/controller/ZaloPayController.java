package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.request.ZaloPayOrder;
import com.danglinh.project_bookstore.service.ZaloPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ZaloPayController {

    private ZaloPayService zaloPayService;

    public ZaloPayController(ZaloPayService zaloPayService) {
        this.zaloPayService = zaloPayService;
    }

    @PostMapping("/zalopay")
    public ResponseEntity<String> createPayment(@RequestBody ZaloPayOrder zaloPayOrder) {
        try {
            String response = zaloPayService.createOrder(zaloPayOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating payment: " + e.getMessage());
        }
    }
    @GetMapping("/order-status/{appTransId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String appTransId) {
        String response = zaloPayService.getOrderStatus(appTransId);
        return ResponseEntity.ok(response);
    }
}
