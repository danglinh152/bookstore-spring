package com.danglinh.project_bookstore.service.error;

import com.danglinh.project_bookstore.domain.DTO.response.RestResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> idInvalidException(IdInvalidException idInvalidException) {
        RestResponse<Object> res = new RestResponse<>();
        res.setError("IdInvalidException");
        res.setMessage(idInvalidException.getMessage());
        res.setStatusCode(400);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(res);
    }
}

