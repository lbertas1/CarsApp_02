package org.example.exception;

public class CarServiceException extends RuntimeException {
    public CarServiceException(String message) {
        super(message);
    }
}
