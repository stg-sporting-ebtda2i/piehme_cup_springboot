package com.stgsporting.piehmecup.exceptions;

public class IconNotFoundException extends NotFoundException {
    public IconNotFoundException(String message) {
        super(message);
    }

    public IconNotFoundException() {
        super("Icon not found");
    }
}
