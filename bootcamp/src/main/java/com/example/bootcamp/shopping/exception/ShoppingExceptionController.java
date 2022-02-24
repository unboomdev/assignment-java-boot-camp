package com.example.bootcamp.shopping.exception;

import com.example.bootcamp.common.ExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ShoppingExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse argumentNotValid(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Bad Request: " + errorMsg, System.currentTimeMillis());
    }

    @ExceptionHandler(QuantityMustBeLeastOneException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse quantityLeastOne(QuantityMustBeLeastOneException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler(ProductsItemEmptyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse itemsEmpty(ProductsItemEmptyException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), e.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse dataNotFound(DataNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage(), System.currentTimeMillis());
    }
}
