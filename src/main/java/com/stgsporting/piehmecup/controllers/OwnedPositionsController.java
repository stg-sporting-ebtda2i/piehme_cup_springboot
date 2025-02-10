package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.OwnedPositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ownedPositions")
public class OwnedPositionsController {
    @Autowired
    private OwnedPositionsService ownedPositionsService;

    @GetMapping("/getOwnedPositions")
    public ResponseEntity<Object> getOwnedPositions(){
        try{
            return ResponseEntity.ok().body(ownedPositionsService.getOwnedPositions());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/buy/{positionId}")
    public ResponseEntity<Object> addPositionToUser(@PathVariable Long positionId){
        try{
            ownedPositionsService.addPositionToUser(positionId);
            return ResponseEntity.ok().body("Position purchased successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/sell/{positionId}")
    public ResponseEntity<Object> removePositionFromUser(@PathVariable Long positionId){
        try{
            ownedPositionsService.removePositionFromUser(positionId);
            return ResponseEntity.ok().body("Position sold successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
