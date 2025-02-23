package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.dtos.UserRegisterDTO;
import com.stgsporting.piehmecup.exceptions.UserAlreadyExistException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.exceptions.UsernameTakenException;
import com.stgsporting.piehmecup.services.UserAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AuthenticationController {

    private final UserAuthenticationService authService;

    AuthenticationController(UserAuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok().body(
                authService.login(userLoginDTO)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.created(URI.create("/register")).body(
                authService.register(userRegisterDTO)
        );
    }
}
