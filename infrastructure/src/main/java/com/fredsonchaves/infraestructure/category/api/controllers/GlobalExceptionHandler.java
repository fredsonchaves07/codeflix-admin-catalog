package com.fredsonchaves.infraestructure.category.api.controllers;

import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.exceptions.NotFoundException;
import com.fredsonchaves.domain.validation.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handlerDomainException(final DomainException exception) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(exception));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(final NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(exception));
    }

    record ApiError(String message, List<Error> errors){

        static ApiError from(final DomainException exception) {
            return new ApiError(exception.getMessage(), exception.getErrors());
        }
    }
}
