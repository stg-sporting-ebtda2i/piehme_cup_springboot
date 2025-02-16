package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.icons.IconDTO;
import com.stgsporting.piehmecup.dtos.icons.IconUploadDTO;
import com.stgsporting.piehmecup.services.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
public class IconController {
    @Autowired
    private IconService iconService;

    @PostMapping("/admin/icons/create")
    public ResponseEntity<Object> createIcon(@ModelAttribute IconUploadDTO icon) {
        iconService.createIcon(icon);
        return ResponseEntity.ok().body("Icon created successfully");
    }

    @PostMapping("/admin/icons/update/{iconName}")
    public ResponseEntity<Object> updateIcon(@ModelAttribute IconUploadDTO icon, @PathVariable String iconName) {
        iconService.updateIcon(iconName, icon);
        return ResponseEntity.ok().body("Icon updated successfully");
    }

    @GetMapping("/icons/{iconName}")
    public ResponseEntity<Object> getIcon(@PathVariable String iconName) {
        return ResponseEntity.ok().body(iconService.getIconByName(iconName));
    }

    @DeleteMapping("/admin/icons/delete/{iconName}")
    public ResponseEntity<Object> deleteIcon(@PathVariable String iconName) {
        iconService.deleteIcon(iconName);
        return ResponseEntity.ok().body("Icon deleted successfully");
    }

    @GetMapping("/icons")
    public ResponseEntity<Object> getIcons() {
        return ResponseEntity.ok().body(iconService.getAllIcons());
    }
}
