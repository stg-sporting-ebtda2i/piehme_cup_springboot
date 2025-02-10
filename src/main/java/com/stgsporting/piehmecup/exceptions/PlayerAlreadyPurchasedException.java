package com.stgsporting.piehmecup.exceptions;

public class PlayerAlreadyPurchasedException extends RuntimeException {
    public PlayerAlreadyPurchasedException(String message) {
        super(message);
    }
}
