package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.SelectPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/selectPosition")
public class SelectPositionController {
    @Autowired
    private SelectPositionService selectPositionService;

    @GetMapping
    public ResponseEntity<Object> getSelectedPosition(){
        return ResponseEntity.ok().body(selectPositionService.getSelectedPosition());
    }

    @PatchMapping("/{positionId}")
    public ResponseEntity<Object> selectPosition(@PathVariable Long positionId){
        selectPositionService.selectPosition(positionId);
        return ResponseEntity.ok().body(Map.of("message", "Position selected"));
    }
}
