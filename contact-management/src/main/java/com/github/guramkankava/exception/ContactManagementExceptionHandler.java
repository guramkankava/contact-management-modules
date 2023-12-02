package com.github.guramkankava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ContactManagementExceptionHandler {

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Object> handleContactNotFound(ContactNotFoundException contactNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contactNotFoundException);
    }
}
