package com.github.guramkankava.exception;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

@JsonIncludeProperties("message")
public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(String message) {
        super(message);
    }

}
