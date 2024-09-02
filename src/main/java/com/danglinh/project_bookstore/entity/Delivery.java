package com.danglinh.project_bookstore.entity;

import lombok.Data;

import java.util.List;

@Data
public class Delivery {
    private int id;
    private String name;
    private String description;
    private double shippingCost;
    private List<Order> listOfOrder;
}
