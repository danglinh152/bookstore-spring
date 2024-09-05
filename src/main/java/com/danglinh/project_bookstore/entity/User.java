package com.danglinh.project_bookstore.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private char gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "buying_address")
    private String buyingAddress;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "listOfFeedback")
    private List<Feedback> listOfFeedback;

    @Column(name = "listOfFarvorite")
    private List<Favorite> listOfFarvorite;

    @Column(name = "listOfRole")
    private List<Role> listOfRole;

    @Column(name = "listOfOrder")
    private List<Order> listOfOrder;
}
