package com.stgsporting.piehmecup.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SchoolYearNotFound extends NotFoundException {
    public SchoolYearNotFound(String message) {
        super(message);
    }

    public SchoolYearNotFound() {
        super("Osra not found");
    }
}
