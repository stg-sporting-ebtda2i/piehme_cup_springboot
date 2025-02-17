package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.dtos.icons.IconUploadDTO;
import com.stgsporting.piehmecup.services.IconService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("")
public class IconController {
    private final IconService iconService;

    public IconController(IconService iconService) {
        this.iconService = iconService;
    }

    @PostMapping("/admin/icons")
    public ResponseEntity<Object> createIcon(@ModelAttribute IconUploadDTO icon) {
        iconService.createIcon(icon);
        return ResponseEntity.ok().body(Map.of("message", "Icon created successfully"));
    }

    @PostMapping("/admin/icons/{iconId}")
    public ResponseEntity<Object> updateIcon(@ModelAttribute IconUploadDTO icon, @PathVariable Long iconId) {
        iconService.updateIcon(iconId, icon);
        return ResponseEntity.ok().body(Map.of("message", "Icon updated successfully"));
    }

    @GetMapping("admin/icons/{iconId}")
    public ResponseEntity<Object> getIconId(@PathVariable Long iconId) {
        return ResponseEntity.ok().body(iconService.getIconById(iconId));
    }

    @GetMapping("/icons/{iconName}")
    public ResponseEntity<Object> getIcon(@PathVariable String iconName) {
        return ResponseEntity.ok().body(iconService.getIconByName(iconName));
    }

    @DeleteMapping("/admin/icons/delete/{iconName}")
    public ResponseEntity<Object> deleteIcon(@PathVariable String iconName) {
        iconService.deleteIcon(iconName);
        return ResponseEntity.ok().body(Map.of("message", "Icon deleted successfully"));
    }

    @GetMapping("/admin/icons")
    public ResponseEntity<Object> getIconsForAdmins(@RequestParam @Nullable Integer page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page == null ? 0 : page);

        return ResponseEntity.ok().body(
                new PaginationDTO<>(iconService.getAllIcons(pageable))
        );
    }

    @GetMapping("/icons")
    public ResponseEntity<Object> getIcons() {
        return ResponseEntity.ok().body(iconService.getAllIcons());
    }
}
