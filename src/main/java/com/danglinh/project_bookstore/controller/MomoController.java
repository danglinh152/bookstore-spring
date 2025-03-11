package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.service.MomoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api")
@RestController
public class MomoController {

    @Autowired
    private MomoService momoService;

    @PostMapping("/momo")
    public String testPayment() {
        String amount = "10000";
        return momoService.createPaymentRequest(amount);
    }

    @GetMapping("/order-status/{orderId}")
    public String checkPaymentStatus(@PathVariable String orderId) {
        String response = momoService.checkPaymentStatus(orderId);
        return response;
    }

}
