package com.danglinh.project_bookstore.util.error;

import com.danglinh.project_bookstore.domain.DTO.response.RestResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {
            BadCredentialsException.class,
            UsernameNotFoundException.class,
    })
    public ResponseEntity<RestResponse<Object>> handleException(Exception e) {
        RestResponse<Object> res = new RestResponse<>();
        res.setError("Exception occured");
        res.setMessage(e.getMessage());
        res.setStatusCode(400);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(res);
    }
}

