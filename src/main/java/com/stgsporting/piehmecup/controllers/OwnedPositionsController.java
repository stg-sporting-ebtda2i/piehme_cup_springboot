package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.OwnedPositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ownedPositions")
public class OwnedPositionsController {
    private final OwnedPositionsService ownedPositionsService;

    public OwnedPositionsController(OwnedPositionsService ownedPositionsService) {
        this.ownedPositionsService = ownedPositionsService;
    }

    @GetMapping("/getOwnedPositions")
    public ResponseEntity<Object> getOwnedPositions() {
        return ResponseEntity.ok().body(ownedPositionsService.getOwnedPositions());
    }

    @PatchMapping("/buy/{positionId}")
    public ResponseEntity<Object> addPositionToUser(@PathVariable Long positionId) {
        ownedPositionsService.addPositionToUser(positionId);

        return ResponseEntity.ok().body(Map.of("message", "Position purchased successfully"));
    }

    @PatchMapping("/sell/{positionId}")
    public ResponseEntity<Object> removePositionFromUser(@PathVariable Long positionId) {
        ownedPositionsService.removePositionFromUser(positionId);

        return ResponseEntity.ok().body(Map.of("message", "Position sold successfully"));
    }
}
