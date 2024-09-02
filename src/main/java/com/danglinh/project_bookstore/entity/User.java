package com.danglinh.project_bookstore.entity;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private char gender;
    private String email;
    private String phoneNumber;
    private String buyingAddress;
    private String shippingAddress;
    private List<Feedback> listOfFeedback;
    private List<Favorite> listOfFarvorite;
    private List<Role> listOfRole;
    private List<Order> listOfOrder;
}
