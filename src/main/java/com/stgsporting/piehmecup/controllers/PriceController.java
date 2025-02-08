package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @PostMapping("/admin/prices/create/{name}/{price}")
    public ResponseEntity<Object> createPrice(@PathVariable String name, @PathVariable Integer price) {
        try{
            priceService.createPrice(name, price);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/admin/prices/update/{name}/{price}")
    public ResponseEntity<Object> updatePrice(@PathVariable String name, @PathVariable Integer price) {
        try{
            priceService.updatePrice(name, price);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/admin/prices/delete/{name}")
    public ResponseEntity<Object> deletePrice(@PathVariable String name) {
        try{
            priceService.deletePrice(name);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/prices/{name}")
    public ResponseEntity<Object> getPrice(@PathVariable String name) {
        Price price = priceService.getPrice(name);

        if (price == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            return ResponseEntity.ok(price);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/prices/all")
    public ResponseEntity<Object> getAllPrices() {
        try{
            return ResponseEntity.ok(priceService.getAllPrices());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
