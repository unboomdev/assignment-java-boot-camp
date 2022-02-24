package com.example.bootcamp.shopping.exception;

public class QuantityMustBeLeastOneException extends RuntimeException {
    public QuantityMustBeLeastOneException(String message) {
        super(message);
    }
}
