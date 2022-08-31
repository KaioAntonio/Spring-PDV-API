package com.pdv.pdv.exceptions;

public class PasswordNotFoundException extends RuntimeException{

    public PasswordNotFoundException(String message) {
        super(message);
    }
}
