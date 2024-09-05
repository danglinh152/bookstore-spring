package com.danglinh.project_bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "description")
    private String description;

    @Column(name = "list_price")
    private double listPrice; // giá niêm yết

    @Column(name = "selling_price")
    private double sellingPrice; // giá bán

    @Column(name = "quantity")
    private int quantity;

    @ManyToMany(mappedBy = "listOfBook")
    private List<Genre> listOfGenre;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private List<Image> listOfImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private List<Feedback> listOfFeedback;

    @ManyToMany
    @JoinTable(
            name = "book_course",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "orderdetail_id"))
    private List<Orderdetails> listOfOrderdetails;

    @Column(name = "title")
    private List<Favorite> listOfFavorite;
}
