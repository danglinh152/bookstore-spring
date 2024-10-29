package com.danglinh.project_bookstore.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {
    private String token;
    private int bookId;
}
