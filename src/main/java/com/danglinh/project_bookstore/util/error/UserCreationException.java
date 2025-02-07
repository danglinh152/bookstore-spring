package com.danglinh.project_bookstore.util.error;

import lombok.Getter;

import java.util.List;

@Getter
public class UserCreationException extends RuntimeException {
    private final List<String> errorMessages;

    public UserCreationException(List<String> errorMessages) {
        super("User creation failed");
        this.errorMessages = errorMessages;
    }

}
