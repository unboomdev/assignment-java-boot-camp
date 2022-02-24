package com.example.bootcamp.shopping.exception;

public class ProductsItemEmptyException extends RuntimeException {
    public ProductsItemEmptyException(String message) {
        super(message);
    }
}
