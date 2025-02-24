package com.stgsporting.piehmecup.exceptions;

public class IconAlreadyPurchasedException extends AlreadyPurchasedException {
    public IconAlreadyPurchasedException(String message) {
        super(message);
    }

    public IconAlreadyPurchasedException() {
        super("Icon already purchased");
    }
}
