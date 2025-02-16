package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stgsporting.piehmecup.services.ButtonsVisibilityService;

@RestController
@RequestMapping("/ostaz/buttons-visibility")
public class ButtonsVisibilityController {
    @Autowired
    private ButtonsVisibilityService buttonsVisibilityService;
    @Autowired
    private AdminService adminService;

    @GetMapping("")
    public ResponseEntity<Object> getVisibility() {
        Admin admin = (Admin) adminService.getAuthenticatable();

        return ResponseEntity.ok(buttonsVisibilityService.findButtonsVisibilityByUserRole(admin.getRole()));
    }

    @PutMapping("{name}/{visible}")
    public void changeVisibility(@PathVariable String name, @PathVariable Boolean visible) {
        buttonsVisibilityService.changeVisibilityByName(name, visible);
    }
}
