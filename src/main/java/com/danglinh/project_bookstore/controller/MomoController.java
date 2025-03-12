package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.request.MomoOrder;
import com.danglinh.project_bookstore.domain.DTO.response.PaymentUrl;
import com.danglinh.project_bookstore.service.MomoService;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequestMapping("/api")
@RestController
public class MomoController {

    private MomoService momoService;

    public MomoController(MomoService momoService) {
        this.momoService = momoService;
    }

    @PostMapping("/momo")
    public ResponseEntity<PaymentUrl> testPayment(@RequestBody MomoOrder momoOrder) throws IdInvalidException {
        PaymentUrl paymentUrl = new PaymentUrl();
        paymentUrl.setPaymentUrl(momoService.createPaymentRequest(momoOrder));
        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/order-status/{orderId}")
    public String checkPaymentStatus(@PathVariable String orderId) {
        String response = momoService.checkPaymentStatus(orderId);
        return response;
    }

}
