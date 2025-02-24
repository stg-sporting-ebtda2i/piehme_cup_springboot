package com.stgsporting.piehmecup.exceptions;

public class PlayerAlreadyPurchasedException extends AlreadyPurchasedException {
    public PlayerAlreadyPurchasedException(String message) {
        super(message);
    }

    public PlayerAlreadyPurchasedException() {
        super("Player already purchased");
    }
}
