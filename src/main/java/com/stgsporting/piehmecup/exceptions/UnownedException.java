package com.stgsporting.piehmecup.exceptions;

public class UnownedException extends RuntimeException {
    public UnownedException(String message) {
        super(message);
    }

    public UnownedException() {
        super("You do not own this");
    }
}
