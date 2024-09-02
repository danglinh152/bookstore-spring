package com.danglinh.project_bookstore.entity;
import lombok.Data;

@Data
public class Image {
    private int id;
    private String name;
    private boolean isIcon;
    private String path; // if path is null => data
    private String data; //
    private Book book;
}
