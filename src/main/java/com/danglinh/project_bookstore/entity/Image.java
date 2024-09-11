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
    @Column(name = "image_id")
    private int imageId;

    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "is_icon")
    private boolean isIcon;

    @Column(name = "path")
    private String path; // if path is null => data

    @Column(name = "data", columnDefinition = "LONGTEXT")
    @Lob
    private String data; //

    @ManyToOne(
            fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH}
    )
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
