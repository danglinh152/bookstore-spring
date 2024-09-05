package com.danglinh.project_bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.List;

@Data
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "shipping_cost")
    private double shippingCost;

    @Column(name = "listOfOrder")
    private List<Order> listOfOrder;
}
