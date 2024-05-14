package com.patients.patients.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("Could not find post " + id);
    }
}