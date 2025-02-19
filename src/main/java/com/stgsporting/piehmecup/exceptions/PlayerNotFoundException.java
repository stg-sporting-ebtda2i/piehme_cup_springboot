package com.stgsporting.piehmecup.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends NotFoundException {
    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException() {
        super("Player not found");
    }
}
