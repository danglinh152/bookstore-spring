package com.danglinh.project_bookstore.domain.entity;

import com.danglinh.project_bookstore.util.security.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
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
    @NotBlank(message = "title cannot be blank!")
    private String title;

    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image; //

    @Column(name = "author", length = 512)
    private String author;

    @Column(name = "isbn", length = 256)
    private String isbn;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "description_details", columnDefinition = "LONGTEXT")
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

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "GMT+7")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "GMT+7")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToMany()
    @JoinTable(name = "book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    @JsonIgnoreProperties("listOfBook")
    private List<Genre> listOfGenre;


    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties("book")
    private List<Image> listOfImage;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties({"book", "user"})
    private List<Feedback> listOfFeedback;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties({"book", "user", "order"})
    private List<OrderDetails> listOfOrderdetails;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties({"book", "user"})
    private List<Favorite> listOfFavorite;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties({"book", "user", "cart"})
    private List<CartDetails> listOfCartdetails;


    @PrePersist
    public void beforeCreate() {
        this.createdAt = Instant.now();
        if (SecurityUtil.getCurrentUser().isEmpty()) {
            this.createdBy = "unknown";
        } else {
            this.createdBy = SecurityUtil.getCurrentUser().get();
        }
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = Instant.now();
        if (SecurityUtil.getCurrentUser().isEmpty()) {
            this.updatedBy = "unknown";
        } else {
            this.updatedBy = SecurityUtil.getCurrentUser().get();
        }
    }
}
