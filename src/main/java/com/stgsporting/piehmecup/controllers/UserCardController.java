package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.CardRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userCard")
public class UserCardController {
    @Autowired
    private CardRatingService cardRatingService;

    @GetMapping
    public ResponseEntity<Object> getUserCardDTO(){
        try {
            return ResponseEntity.ok(cardRatingService.getUserCardDTO());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserCardDTO(@PathVariable Long userId){
        try {
            return ResponseEntity.ok(cardRatingService.getUserCardDTO(userId));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
