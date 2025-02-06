package com.danglinh.project_bookstore.domain.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long total;
}
