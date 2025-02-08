package com.danglinh.project_bookstore.domain.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateDTO {
    private String activateCode;
    private UserLogin userLogin;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {
        private String username;
        private String email;
        private String firstName;
        private String lastName;
    }
}
