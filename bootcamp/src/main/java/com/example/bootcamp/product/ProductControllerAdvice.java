package com.example.bootcamp.product;

import com.example.bootcamp.common.ExceptionResponse;
import com.example.bootcamp.product.exception.ProductNotFoundException;
import com.example.bootcamp.product.exception.SearchNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse productNotFound(ProductNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler(SearchNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse searchNotFound(SearchNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage(), System.currentTimeMillis());
    }
}
