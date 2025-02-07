package com.danglinh.project_bookstore.domain.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
