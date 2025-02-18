package com.stgsporting.piehmecup.exceptions;

public class LiturgyNotFoundException extends NotFoundException {
    public LiturgyNotFoundException(String message) {
        super(message);
    }

    public LiturgyNotFoundException() {
        super("Liturgy not found");
    }
}
