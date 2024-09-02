package com.danglinh.project_bookstore.entity;

import lombok.Data;

@Data
public class Favorite {
    private int id;
    private User user;
    private Book book;
}
