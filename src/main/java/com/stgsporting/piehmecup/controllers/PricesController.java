package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.services.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class PricesController {
    @Autowired
    private PricesService pricesService;

    @PostMapping("/admin/prices/create/{name}/{price}")
    public ResponseEntity<Object> createPrice(@PathVariable String name, @PathVariable Integer price) {
        try{
            pricesService.createPrice(name, price);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/admin/prices/update/{name}/{price}")
    public ResponseEntity<Object> updatePrice(@PathVariable String name, @PathVariable Integer price) {
        try{
            pricesService.updatePrice(name, price);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/admin/prices/delete/{name}")
    public ResponseEntity<Object> deletePrice(@PathVariable String name) {
        try{
            pricesService.deletePrice(name);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/prices/{name}")
    public ResponseEntity<Object> getPrice(@PathVariable String name) {
        Price price = pricesService.getPrice(name);

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
            return ResponseEntity.ok(pricesService.getAllPrices());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
