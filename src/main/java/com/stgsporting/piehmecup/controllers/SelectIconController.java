package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.SelectIconService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selectIcon")
public class SelectIconController {
    @Autowired
    private SelectIconService selectedIconService;

    @GetMapping
    public ResponseEntity<Object> getSelectedIcon(){
        try {
            return ResponseEntity.ok().body(selectedIconService.getSelectedIcon());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{iconId}")
    public ResponseEntity<Object> selectIcon(@PathVariable Long iconId){
        try {
            selectedIconService.selectIcon(iconId);
            return ResponseEntity.ok().body("Icon selected successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
