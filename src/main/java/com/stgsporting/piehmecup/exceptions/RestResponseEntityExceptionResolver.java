package com.stgsporting.piehmecup.exceptions;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { UserNotFoundException.class, SchoolYearNotFound.class, PlayerNotFoundException.class, IconNotFoundException.class, PositionNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(
            NotFoundException ex, WebRequest request) {
        return handleExceptionDefault(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { UsernameTakenException.class, IconAlreadyPurchasedException.class, PlayerAlreadyPurchasedException.class, InsufficientCoinsException.class })
    protected ResponseEntity<Object> handleUsernameTaken(UsernameTakenException ex, WebRequest request) {
        return handleExceptionDefault(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { InvalidCredentialsException.class })
    protected ResponseEntity<Object> handleInvalidCred(InvalidCredentialsException ex, WebRequest request) {
        return handleExceptionDefault(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { UnauthorizedAccessException.class })
    protected ResponseEntity<Object> handleUnauthorized(UnauthorizedAccessException ex, WebRequest request) {
        return handleExceptionDefault(ex, HttpStatus.UNAUTHORIZED, request);
    }

    private ResponseEntity<Object> handleExceptionDefault(Exception ex, HttpStatus status, WebRequest request) {
        JSONObject response = new JSONObject();
        response.put("message", ex.getMessage());

        return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }
}
