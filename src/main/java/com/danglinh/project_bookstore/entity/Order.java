package com.danglinh.project_bookstore.entity;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order {
    private int id;
    private Date date;
    private String buyingAddress;
    private String shippingAddress;
    private double productCost;
    private double paymentCost;
    private double shippingCost;
    private double total;
    private List<Orderdetails> listOfOrderdetails;
    private User user;
    private Payment payment;
    private Delivery delivery;
}
