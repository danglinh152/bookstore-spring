package com.danglinh.project_bookstore.domain.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePaginationDTO {
    private Meta meta;
    private Object data;
}
