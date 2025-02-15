package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.CoinsDTO;
import com.stgsporting.piehmecup.services.CoinsService;
import com.stgsporting.piehmecup.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ostaz/coins")
public class CoinsController {
    @Autowired
    private UserService userService;

    @Autowired
    private CoinsService coinsService;

    @GetMapping
    public ResponseEntity<Object> getCoins() {
        try {
            return ResponseEntity.ok(userService.getCoins());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/add")
    public ResponseEntity<Object> addCoins(@RequestBody CoinsDTO coinsDTO) {
        try {
            coinsService.addCoins(coinsDTO);
            return ResponseEntity.ok("Coins added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/remove")
    public ResponseEntity<Object> removeCoins(@RequestBody CoinsDTO coinsDTO) {
        try {
            coinsService.removeCoins(coinsDTO);
            return ResponseEntity.ok("Coins removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
