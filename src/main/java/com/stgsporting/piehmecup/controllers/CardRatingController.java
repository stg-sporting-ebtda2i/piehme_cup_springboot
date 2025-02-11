package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.CardRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cardRating")
public class CardRatingController {
    @Autowired
    private CardRatingService cardRatingService;

    @GetMapping
    public ResponseEntity<Object> getRating(){
        try {
            return ResponseEntity.ok(cardRatingService.getCardRating());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{delta}")
    public ResponseEntity<Object> updateRating(@PathVariable Integer delta){
        try {
            cardRatingService.updateRating(delta);
            return ResponseEntity.ok().body("Rating changed successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
