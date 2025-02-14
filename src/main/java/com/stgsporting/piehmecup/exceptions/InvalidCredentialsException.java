package com.stgsporting.piehmecup.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super("Incorrect username or password");
    }
}
