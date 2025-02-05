package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PlayerDTO;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/admin/players/create")
    public ResponseEntity<Object> createPlayer(@RequestBody PlayerDTO player){
        try{
            playerService.createPlayer(player);
            return ResponseEntity.ok().body("Player created successfully");
        } catch (IllegalArgumentException e){
            return ResponseEntity.unprocessableEntity().body("Player cannot be null");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("An error occurred while saving player");
        }
    }

    @GetMapping("/player/{playerName}")
    public ResponseEntity<Object> getPlayer(@PathVariable String playerName){
        try{
            return ResponseEntity.ok().body(playerService.getPlayerByName(playerName));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/players/delete/{playerName}")
    public ResponseEntity<Object> deletePlayer(@PathVariable String playerName){
        try{
            playerService.deletePlayer(playerName);
            return ResponseEntity.ok().body("Player deleted successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/admin/players/update/{playerName}")
    public ResponseEntity<Object> updatePlayer(@PathVariable String playerName, @RequestBody PlayerDTO player){
        try{
            playerService.updatePlayer(playerName, player);
            return ResponseEntity.ok().body("Player updated successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/players/{position}")
    public ResponseEntity<Object> getPlayersByPosition(@PathVariable String position){
        try{
            return ResponseEntity.ok().body(playerService.getPlayersByPosition(Positions.valueOf(position.toUpperCase())));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
