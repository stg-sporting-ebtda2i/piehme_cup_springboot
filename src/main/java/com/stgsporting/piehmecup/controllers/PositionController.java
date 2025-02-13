package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping("/positions")
    public ResponseEntity<Object> getAllPositions(){
        try {
            return ResponseEntity.ok(positionService.getAllPositions());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching positions");
        }
    }

    @PutMapping("/admin/positions/update/{name}/{price}")
    public void updatePrice(@PathVariable String name, @PathVariable Integer price){
        positionService.updatePrice(name, price);
    }
}
