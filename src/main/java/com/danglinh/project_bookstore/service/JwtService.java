package com.danglinh.project_bookstore.service;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    public String generateJwtToken(String userName);

    public Claims extractAllJwtToken(String token);

    public <T> T extractJwtToken(String token, Function<Claims, T> claimsTFunction);

    public Date extractJwtTokenExpiration(String token);

    public String extractUserNameFromJwtToken(String token);

    public Boolean isTokenExpired(String token);

    public Boolean validateJwtToken(String token, UserDetails userDetails);
}
