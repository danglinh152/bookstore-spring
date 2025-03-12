package com.danglinh.project_bookstore.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomoOrder {
    private String orderInfo;
    private Long amount;
}
