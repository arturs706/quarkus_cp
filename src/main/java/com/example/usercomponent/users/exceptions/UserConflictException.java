package com.example.usercomponent.users.exceptions;

public class UserConflictException extends RuntimeException {
    private final String errorMessage;

    public UserConflictException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}