package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/coins")
public class CoinsController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getCoins() {
        return ResponseEntity.ok(
                Map.of("coins", userService.getCoins())
        );
    }
}
