package com.danglinh.project_bookstore.util.error;

import java.util.List;

public class UserCreationException extends RuntimeException {
    private final List<String> errorMessages;

    public UserCreationException(List<String> errorMessages) {
        super("User creation failed");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
