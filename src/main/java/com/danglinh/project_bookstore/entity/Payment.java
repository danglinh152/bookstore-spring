package com.danglinh.project_bookstore.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "payment_cost")
    private double paymentCost;

    @Column(name = "listOfOrder")
    private List<Order> listOfOrder;
}
