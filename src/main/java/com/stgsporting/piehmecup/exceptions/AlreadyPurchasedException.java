package com.stgsporting.piehmecup.exceptions;

public class AlreadyPurchasedException extends RuntimeException {
    public AlreadyPurchasedException(String message) {
        super(message);
    }

    public AlreadyPurchasedException() {
        super("Already purchased");
    }
}
