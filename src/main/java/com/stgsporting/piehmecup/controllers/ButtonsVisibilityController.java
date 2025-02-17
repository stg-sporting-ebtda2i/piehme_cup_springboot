package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stgsporting.piehmecup.services.ButtonsVisibilityService;

@RestController
public class ButtonsVisibilityController {
    private final ButtonsVisibilityService buttonsVisibilityService;
    private final AdminService adminService;
    public ButtonsVisibilityController(ButtonsVisibilityService buttonsVisibilityService, AdminService adminService) {
        this.buttonsVisibilityService = buttonsVisibilityService;
        this.adminService = adminService;
    }

    @GetMapping("/ostaz/buttons-visibility")
    public ResponseEntity<Object> getVisibility() {
        Admin admin = (Admin) adminService.getAuthenticatable();

        return ResponseEntity.ok(buttonsVisibilityService.findButtonsVisibilityByUserRole(admin.getRole()));
    }

    @GetMapping("/buttons-visibility")
    public ResponseEntity<Object> getVisibilityForUser() {
        return ResponseEntity.ok(
                buttonsVisibilityService.getAllButtonsVisibility().stream().map(ButtonsVisibility::getName).toList()
        );
    }

    @PutMapping("/ostaz/buttons-visibility/{name}/{visible}")
    public void changeVisibility(@PathVariable String name, @PathVariable Boolean visible) {
        buttonsVisibilityService.changeVisibilityByName(name, visible);
    }
}
