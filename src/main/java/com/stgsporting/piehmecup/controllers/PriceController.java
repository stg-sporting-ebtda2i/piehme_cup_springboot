package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @PostMapping("/admin/prices")
    public ResponseEntity<Object> createPrice(@RequestBody Price price) {
        return ResponseEntity.ok(priceService.save(price));
    }

    @PutMapping("/admin/prices/{priceId}")
    public ResponseEntity<Object> updatePrice(@PathVariable Long priceId, @RequestBody Price price) {
        price.setId(priceId);
        priceService.save(price);

        return ResponseEntity.ok(Map.of("message", "Price updated successfully"));
    }

    @DeleteMapping("/admin/prices/{priceId}")
    public ResponseEntity<Object> deletePrice(@PathVariable Long priceId) {
        priceService.deletePrice(priceId);

        return ResponseEntity.ok(Map.of("message", "Price deleted successfully"));
    }

    @GetMapping("/prices/{priceId}")
    public ResponseEntity<Object> getPrice(@PathVariable Long priceId) {
        return ResponseEntity.ok(priceService.getPriceById(priceId));
    }

    @GetMapping("/admin/prices")
    public ResponseEntity<Object> index(@RequestParam Integer page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);

        return ResponseEntity.ok(
                new PaginationDTO<>(priceService.getPrices(pageable))
        );
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
