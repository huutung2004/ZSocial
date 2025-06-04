package com.example.postservice.exception;

public class IsNotExistException extends RuntimeException {
    public IsNotExistException(String message) {
        super(message);
    }
}
