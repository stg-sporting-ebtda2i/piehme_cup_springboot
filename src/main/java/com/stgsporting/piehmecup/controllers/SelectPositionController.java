package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.SelectPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selectPosition")
public class SelectPositionController {
    @Autowired
    private SelectPositionService selectPositionService;

    @GetMapping
    public ResponseEntity<Object> getSelectedPosition(){
        try {
            return ResponseEntity.ok().body(selectPositionService.getSelectedPosition());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{positionId}")
    public ResponseEntity<Object> selectPosition(@PathVariable Long positionId){
        try {
            selectPositionService.selectPosition(positionId);
            return ResponseEntity.ok().body("Position selected successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
