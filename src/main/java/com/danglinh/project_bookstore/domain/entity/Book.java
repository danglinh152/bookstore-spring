package com.danglinh.project_bookstore.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "title", length = 256)
    private String title;

    @Column(name = "author", length = 512)
    private String author;

    @Column(name = "isbn", length = 256)
    private String isbn;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "description_details", columnDefinition = "LONGTEXT  ")
    private String descriptionDetails;

    @Column(name = "info_details", columnDefinition = "LONGTEXT")
    private String infoDetails;

    @Column(name = "list_price")
    private double listPrice; // giá niêm yết

    @Column(name = "selling_price")
    private double sellingPrice; // giá bán

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "avg_rate")
    private double avgRate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinTable(name = "book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> listOfGenre;


    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> listOfImage;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Feedback> listOfFeedback;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    private List<Orderdetails> listOfOrderdetails;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Favorite> listOfFavorite;
}
