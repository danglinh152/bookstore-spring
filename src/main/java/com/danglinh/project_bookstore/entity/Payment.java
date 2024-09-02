package com.danglinh.project_bookstore.entity;

import java.util.List;

import lombok.Data;

@Data
public class Payment {
    private int id;
    private String name;
    private String description;
    private double paymentCost;
    private List<Order> listOfOrder;
}
