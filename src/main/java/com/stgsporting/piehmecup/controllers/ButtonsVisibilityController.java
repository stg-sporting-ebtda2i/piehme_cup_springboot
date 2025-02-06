package com.stgsporting.piehmecup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stgsporting.piehmecup.services.ButtonsVisibilityService;

@RestController
@RequestMapping("/ostaz/buttons-visibility")
public class ButtonsVisibilityController {
    @Autowired
    private ButtonsVisibilityService buttonsVisibilityService;

    @GetMapping("{role}")
    public ResponseEntity<Object> getVisibility(@PathVariable String role) {
        try{
            return ResponseEntity.ok(buttonsVisibilityService.findButtonsVisibilityByUserRole(role));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role not found");
        }
    }

    @PutMapping("{name}/{visible}")
    public void changeVisibility(@PathVariable String name, @PathVariable Boolean visible) {
        buttonsVisibilityService.changeVisibilityByName(name, visible);
    }
}
