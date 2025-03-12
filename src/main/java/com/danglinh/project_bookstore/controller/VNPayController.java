package com.danglinh.project_bookstore.controller;

import com.danglinh.project_bookstore.domain.DTO.request.VNPayOrder;
import com.danglinh.project_bookstore.domain.DTO.response.PaymentUrl;
import com.danglinh.project_bookstore.domain.DTO.response.VNPayDTO;
import com.danglinh.project_bookstore.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class VNPayController {

    private VNPayService vnPayService;

    @Value("${vnp_ReturnUrl}")
    private String vnp_Returnurl;

    public VNPayController(VNPayService vnPayService) {
        this.vnPayService = vnPayService;
    }

    @PostMapping("/vnpay")
    public ResponseEntity<PaymentUrl> vnpay(HttpServletRequest request,@RequestBody VNPayOrder vnPayOrder) {
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, Integer.parseInt(vnPayOrder.getAmount().toString()), vnPayOrder.getOrderInfo(), vnp_Returnurl);

        PaymentUrl paymentUrl = new PaymentUrl();
        paymentUrl.setPaymentUrl(vnpayUrl);
        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/vnpay-callback")
    public ResponseEntity<?> vnpayCallback(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        // Lấy thông tin từ request
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String bankCode = request.getParameter("vnp_BankCode");
        String bankTranNo = request.getParameter("vnp_BankTranNo");
        String cardType = request.getParameter("vnp_CardType");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String orderRef = request.getParameter("vnp_TxnRef");

        // Tạo đối tượng VNPayDTO và thiết lập các giá trị
        VNPayDTO vnPayDTO = new VNPayDTO();
        vnPayDTO.setOrderInfo(orderInfo);
        vnPayDTO.setTotalPrice(totalPrice);
        vnPayDTO.setPaymentTime(paymentTime);
        vnPayDTO.setTransactionId(transactionId);
        vnPayDTO.setBankCode(bankCode);
        vnPayDTO.setBankTranNo(bankTranNo);
        vnPayDTO.setCardType(cardType);
        vnPayDTO.setTransactionNo(transactionNo);
        vnPayDTO.setOrderRef(orderRef);

        // Trả về thông tin VNPayDTO dưới dạng chuỗi
        return paymentStatus == 1 ? ResponseEntity.ok(vnPayDTO) : ResponseEntity.badRequest().body("Payment failed");
    }

}
