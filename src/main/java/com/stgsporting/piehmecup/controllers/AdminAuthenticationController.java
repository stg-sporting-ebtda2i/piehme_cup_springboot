package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.LoginDTO;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.services.AdminAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminAuthenticationController {

    private final AdminAuthenticationService authService;

    AdminAuthenticationController(AdminAuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO userLoginDTO){
        return ResponseEntity.ok().body(
                authService.login(userLoginDTO)
        );
    }
}
