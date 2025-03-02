package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.services.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
