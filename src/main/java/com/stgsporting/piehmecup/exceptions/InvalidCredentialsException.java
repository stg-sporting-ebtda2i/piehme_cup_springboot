package com.stgsporting.piehmecup.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Incorrect username or password");
    }
}
