package com.stgsporting.piehmecup.exceptions;

public class UnownedPositionException extends RuntimeException {
    public UnownedPositionException(String message) {
        super(message);
    }

    public UnownedPositionException() {
        super("Position selected is not owned by the user");
    }
}
