package com.danglinh.project_bookstore.domain.DTO.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutDTO {
    private int userId;
    private int bookId;
    private int quantity;
}
