package com.code.salesappbackend.exceptions;

import com.code.salesappbackend.dtos.responses.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseError handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseError handleDataNotFoundException(DataNotFoundException ex) {
        return new ResponseError(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataExistsException.class)
    public ResponseError handleDataExistsException(DataExistsException ex) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OutOfInStockException.class)
    public ResponseError handleOutOfInStockException(OutOfInStockException ex) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()));
    }




}
