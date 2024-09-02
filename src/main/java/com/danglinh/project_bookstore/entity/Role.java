package com.danglinh.project_bookstore.entity;

import java.util.List;

import lombok.Data;

@Data
public class Role {
    private int id;
    private String roleName;
    private List<User> listOfUser;
}
