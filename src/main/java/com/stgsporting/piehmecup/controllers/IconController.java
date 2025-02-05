package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.IconDTO;
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
    public ResponseEntity<Object> createIcon(@RequestBody IconDTO icon){
        try{
            iconService.createIcon(icon);
            return ResponseEntity.ok().body("Icon created successfully");
        } catch (IllegalArgumentException e){
            return ResponseEntity.unprocessableEntity().body("Icon cannot be null");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("An error occurred while saving icon");
        }
    }

    @PostMapping("/admin/icons/update/{iconName}")
    public ResponseEntity<Object> updateIcon(@RequestBody IconDTO icon, @PathVariable String iconName){
        try{
            iconService.updateIcon(iconName, icon);
            return ResponseEntity.ok().body("Icon updated successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/icons/{iconName}")
    public ResponseEntity<Object> getIcon(@PathVariable String iconName){
        try{
            return ResponseEntity.ok().body(iconService.getIconByName(iconName));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/icons/delete/{iconName}")
    public ResponseEntity<Object> deleteIcon(@PathVariable String iconName){
        try{
            iconService.deleteIcon(iconName);
            return ResponseEntity.ok().body("Icon deleted successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/icons")
    public ResponseEntity<Object> getIcons(){
        try{
            return ResponseEntity.ok().body(iconService.getAllIcons());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
