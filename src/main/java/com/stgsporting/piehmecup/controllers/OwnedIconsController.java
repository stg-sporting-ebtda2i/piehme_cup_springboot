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
        try{
            return ResponseEntity.ok().body(ownedIconsService.getOwnedIcons());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/buy/{iconId}")
    public ResponseEntity<Object> addIconToUser(@PathVariable Long iconId){
        try{
            ownedIconsService.addIconToUser(iconId);
            return ResponseEntity.ok().body("Icon purchased successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/sell/{iconId}")
    public ResponseEntity<Object> removeIconFromUser(@PathVariable Long iconId){
        try{
            ownedIconsService.removeIconFromUser(iconId);
            return ResponseEntity.ok().body("Icon sold successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
