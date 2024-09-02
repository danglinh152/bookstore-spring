package com.danglinh.project_bookstore.entity;

import jakarta.persistence.Id;

import java.util.List;

import lombok.Data;

@Data
public class Genre {
    @Id
    private int id;
    private String name;
    private List<Book> listOfBook;
}
