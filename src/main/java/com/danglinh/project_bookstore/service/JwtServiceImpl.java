package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.DAO.UserRepository;
import com.danglinh.project_bookstore.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtServiceImpl implements JwtService {
    public static final String JWT_SECRET = "0355919660512648559A1B2C3D4A4B5C6D7A8B9C100355919660512648559A1B2C3D4A4B5C6D7A8B9C100355919660512648559A1B2C3D4A4B5C6D7A8B9C10";

    @Autowired
    private UserServiceImpl userService;

    @Override
    public String generateJwtToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        User user = new User();
        user = userService.findByUsername(userName);
        claims.put("avatar", user.getAvatar());
        return createToken(claims, userName);
    }

    @Override
    public Claims extractAllJwtToken(String token) {
        return Jwts.parser().setSigningKey(getSignedKey()).parseClaimsJws(token).getBody();
    }

    @Override
    public <T> T extractJwtToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllJwtToken(token);
        return claimsTFunction.apply(claims);
    }

    @Override
    public Date extractJwtTokenExpiration(String token) {
        return extractJwtToken(token, Claims::getExpiration);
    }

    @Override
    public String extractUserNameFromJwtToken(String token) {
        return extractJwtToken(token, Claims::getSubject);
    }



    @Override
    public Boolean isTokenExpired(String token) {
        return extractJwtToken(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    @Override
    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = (String) extractJwtToken(token, Claims::getSubject);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String createToken(Map<String, Object> claims, String userName) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60000)) //expired after 30 phut
                .signWith(SignatureAlgorithm.HS256, getSignedKey())
                .compact();
    }

    private Key getSignedKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
