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
        return ResponseEntity.ok(cardRatingService.getCardRating());
    }

    @PatchMapping("/{delta}")
    public ResponseEntity<Object> updateRating(@PathVariable Integer delta){
        cardRatingService.updateRating(delta);
        return ResponseEntity.ok().body("Rating changed successfully");
    }
}
