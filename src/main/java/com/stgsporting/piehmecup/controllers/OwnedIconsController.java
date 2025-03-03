package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.OwnedIconsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ownedIcons")
public class OwnedIconsController {
    @Autowired
    private OwnedIconsService ownedIconsService;

    @GetMapping("/getOwnedIcons")
    public ResponseEntity<Object> getOwnedIcons(){
        return ResponseEntity.ok().body(ownedIconsService.getOwnedIcons());
    }

    @PatchMapping("/buy/{iconId}")
    public ResponseEntity<Object> addIconToUser(@PathVariable Long iconId){
        ownedIconsService.addIconToUser(iconId);
        return ResponseEntity.ok().body("Icon purchased successfully");
    }

    @PatchMapping("/sell/{iconId}")
    public ResponseEntity<Object> removeIconFromUser(@PathVariable Long iconId){
        ownedIconsService.removeIconFromUser(iconId);
        return ResponseEntity.ok().body("Icon sold successfully");
    }
}
