package com.danglinh.project_bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Data
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_icon")
    private boolean isIcon;

    @Column(name = "path")
    private String path; // if path is null => data

    @Column(name = "data")
    private String data; //

    @Column(name = "book")
    private Book book;
}
