package com.danglinh.project_bookstore.domain.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VNPayDTO {
    private String orderInfo;
    private String paymentTime;
    private String transactionId;
    private String totalPrice;
    private String bankCode;
    private String bankTranNo;
    private String cardType;
    private String transactionNo;
    private String orderRef;
}
