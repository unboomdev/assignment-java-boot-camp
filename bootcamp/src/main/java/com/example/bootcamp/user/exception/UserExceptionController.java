package com.example.bootcamp.user.exception;

import com.example.bootcamp.common.ExceptionResponse;
import com.example.bootcamp.user.exception.AddressNotFoundException;
import com.example.bootcamp.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse userNotFound(UserNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage(), System.currentTimeMillis());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse addressNotFound(AddressNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), e.getMessage(), System.currentTimeMillis());
    }
}
