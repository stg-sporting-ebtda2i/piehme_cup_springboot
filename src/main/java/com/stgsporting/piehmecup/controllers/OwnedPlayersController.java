package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.OwnedPlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ownedPlayers")
public class OwnedPlayersController {
    @Autowired
    private OwnedPlayersService ownedPlayersService;

    // get the lineup of the user
    @GetMapping("/getLineup")
    public ResponseEntity<Object> getLineup(){
        try{
            return ResponseEntity.ok().body(ownedPlayersService.getLineup());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getLineup/{userId}")
    public ResponseEntity<Object> getLineup(@PathVariable Long userId){
        try {
            return ResponseEntity.ok().body(ownedPlayersService.getLineup(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/buy/{playerId}")
    public ResponseEntity<Object> addPlayerToUser(@PathVariable Long playerId){
        try{
            ownedPlayersService.addPlayerToUser(playerId);
            return ResponseEntity.ok().body("Player purchased successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/sell/{playerId}")
    public ResponseEntity<Object> removePlayerFromUser(@PathVariable Long playerId){
        try{
            ownedPlayersService.removePlayerFromUser(playerId);
            return ResponseEntity.ok().body("Player sold successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
