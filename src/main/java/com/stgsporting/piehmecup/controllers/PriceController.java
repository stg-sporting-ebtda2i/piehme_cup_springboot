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
        priceService.createPrice(name, price);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/prices/update/{name}/{price}")
    public ResponseEntity<Object> updatePrice(@PathVariable String name, @PathVariable Integer price) {
        priceService.updatePrice(name, price);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/prices/delete/{name}")
    public ResponseEntity<Object> deletePrice(@PathVariable String name) {
        priceService.deletePrice(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/prices/{name}")
    public ResponseEntity<Object> getPrice(@PathVariable String name) {
        Price price = priceService.getPrice(name);

        if (price == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(price);
    }

    @GetMapping("/prices/all")
    public ResponseEntity<Object> getAllPrices() {
        return ResponseEntity.ok(priceService.getAllPrices());
    }

    @GetMapping("/prices")
    public ResponseEntity<Object> getAllPricesForUser() {
        return ResponseEntity.ok(priceService.getAllPricesForUser());
    }
}
