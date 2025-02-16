package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.players.PlayerUploadDTO;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/admin/players/create")
    public ResponseEntity<Object> createPlayer(@ModelAttribute PlayerUploadDTO player){
        playerService.createPlayer(player);
        return ResponseEntity.ok().body(Map.of("message", "Player created successfully"));
    }

    @PutMapping("/admin/players/update/{playerName}")
    public ResponseEntity<Object> updatePlayer(@PathVariable String playerName, @ModelAttribute PlayerUploadDTO player) {
        playerService.updatePlayer(playerName, player);
        return ResponseEntity.ok().body(Map.of("message", "Player updated successfully"));
    }

    @GetMapping("/player/{playerName}")
    public ResponseEntity<Object> getPlayer(@PathVariable String playerName){
        return ResponseEntity.ok().body(playerService.getPlayerByName(playerName));
    }

    @DeleteMapping("/admin/players/delete/{playerName}")
    public ResponseEntity<Object> deletePlayer(@PathVariable String playerName) {
        playerService.deletePlayer(playerName);
        return ResponseEntity.ok().body(Map.of("message", "Player deleted successfully"));
    }

    @GetMapping("/players/{position}")
    public ResponseEntity<Object> getPlayersByPosition(@PathVariable String position) {
        return ResponseEntity.ok().body(playerService.getPlayersByPosition(Positions.valueOf(position.toUpperCase())));
    }
}
