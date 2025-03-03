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

    @GetMapping("/getLineup")
    public ResponseEntity<Object> getLineup(){
        return ResponseEntity.ok().body(ownedPlayersService.getLineup());
    }

    @GetMapping("/getLineup/{userId}")
    public ResponseEntity<Object> getLineup(@PathVariable Long userId){
        return ResponseEntity.ok().body(ownedPlayersService.getLineup(userId));
    }

    @PatchMapping("/buy/{playerId}")
    public ResponseEntity<Object> addPlayerToUser(@PathVariable Long playerId){
        ownedPlayersService.addPlayerToUser(playerId);
        return ResponseEntity.ok().body("Player purchased successfully");
    }

    @PatchMapping("/sell/{playerId}")
    public ResponseEntity<Object> removePlayerFromUser(@PathVariable Long playerId){
        ownedPlayersService.removePlayerFromUser(playerId);
        return ResponseEntity.ok().body("Player sold successfully");
    }
}
