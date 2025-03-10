package com.danglinh.project_bookstore.util.error;

import com.danglinh.project_bookstore.domain.DTO.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {
            Exception.class
//            UsernameNotFoundException.class  //no needed
    })
    public ResponseEntity<RestResponse<Object>> handleEx(Exception e) {
        RestResponse<Object> res = new RestResponse<>();
        res.setError("Exception occured");
        res.setMessage(e.getMessage());
        res.setStatusCode(400);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(res);
    }


    @ExceptionHandler(value = {
            BadCredentialsException.class,
            IOException.class
//            UsernameNotFoundException.class  //no needed
    })
    public ResponseEntity<RestResponse<Object>> handleException(Exception e) {
        RestResponse<Object> res = new RestResponse<>();
        res.setError("Exception occured");
        res.setMessage(e.getMessage());
        res.setStatusCode(400);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(res);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        RestResponse<Object> res = new RestResponse<>();
        res.setError(e.getBody().getDetail());
        if (fieldErrorList.size() > 1) {
            List<String> stringErrorList = new ArrayList<>();
            for (FieldError fieldError : fieldErrorList) {
                stringErrorList.add(fieldError.getDefaultMessage());
            }
            res.setMessage(stringErrorList);
        } else {
            res.setMessage(fieldErrorList.get(0).getDefaultMessage());
        }
        res.setStatusCode(400);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(res);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<RestResponse<Object>> handleUserCreationException(UserCreationException e) {
        RestResponse<Object> res = new RestResponse<>();
        res.setError("Exception occurred");
        res.setStatusCode(400); // Bad Request
        res.setMessage(e.getErrorMessages());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdInvalidException(IdInvalidException e) {
        RestResponse<Object> res = new RestResponse<>();
        res.setError("Exception occured");
        res.setMessage(e.getMessage());
        res.setStatusCode(400);
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(res);
    }

}

