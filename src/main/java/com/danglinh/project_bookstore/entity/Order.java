package com.danglinh.project_bookstore.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "buying_address")
    private String buyingAddress;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "product_cost")
    private double productCost;

    @Column(name = "payment_cost")
    private double paymentCost;

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "total")
    private double total;

    @Column(name = "listOfOrderdetails")
    private List<Orderdetails> listOfOrderdetails;

    @Column(name = "user")
    private User user;

    @Column(name = "payment")
    private Payment payment;

    @Column(name = "delivery")
    private Delivery delivery;
}
