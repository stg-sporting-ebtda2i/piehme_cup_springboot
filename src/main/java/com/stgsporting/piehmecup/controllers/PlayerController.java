package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.dtos.players.PlayerUploadDTO;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.services.PlayerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/admin/players")
    public ResponseEntity<Object> index(@RequestParam @Nullable Integer page) {
        return ResponseEntity.ok().body(
                new PaginationDTO<>(playerService.getPlayers(PageRequest.of(page == null ? 0 : page, 10)))
        );
    }

    @PostMapping("/admin/players")
    public ResponseEntity<Object> createPlayer(@ModelAttribute PlayerUploadDTO player){
        playerService.createPlayer(player);
        return ResponseEntity.ok().body(Map.of("message", "Player created successfully"));
    }

    @PutMapping("/admin/players/{playerId}")
    public ResponseEntity<Object> updatePlayer(@PathVariable Long playerId, @ModelAttribute PlayerUploadDTO player) {
        playerService.updatePlayer(playerId, player);
        return ResponseEntity.ok().body(Map.of("message", "Player updated successfully"));
    }

    @GetMapping("admin/players/{playerId}")
    public ResponseEntity<Object> getPlayerId(@PathVariable Long playerId){
        return ResponseEntity.ok().body(playerService.getPlayerById(playerId));
    }

    @GetMapping("/player/{playerName}")
    public ResponseEntity<Object> getPlayer(@PathVariable String playerName){
        return ResponseEntity.ok().body(playerService.getPlayerByName(playerName));
    }

    @DeleteMapping("/admin/players/{playerName}/delete")
    public ResponseEntity<Object> deletePlayer(@PathVariable String playerName) {
        playerService.deletePlayer(playerName);
        return ResponseEntity.ok().body(Map.of("message", "Player deleted successfully"));
    }

    @GetMapping("/players/{position}")
    public ResponseEntity<Object> getPlayersByPosition(@PathVariable String position) {
        return ResponseEntity.ok().body(playerService.getPlayersByPosition(position.toUpperCase()));
    }
}
