package com.danglinh.project_bookstore.domain.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswd {
    public String otp;
    public Instant exp;
}
