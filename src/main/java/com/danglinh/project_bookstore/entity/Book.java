package com.danglinh.project_bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;


@Data
public class Book {
    @Id
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private double listPrice; // giá niêm yết
    private double sellingPrice; // giá bán
    private int quantity;
    private List<Genre> listOfGenre;
    private List<Image> listOfImage;
    private List<Feedback> listOfFeedback;
    private List<Orderdetails> listOfOrderdetails;
    private List<Favorite> listOfFavorite;
}
