package com.danglinh.project_bookstore.entity;

import lombok.Data;

@Data
public class Orderdetails {
    private long id;
    private Book book;
    private int quantity;
    private double price;
    private Order order;
}
