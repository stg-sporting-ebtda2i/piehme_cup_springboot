package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.dtos.PriceDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.services.AdminService;
import com.stgsporting.piehmecup.services.PriceService;
import com.stgsporting.piehmecup.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class PriceController {
    private final PriceService priceService;
    private final AdminService adminService;
    private final UserService userService;

    public PriceController(PriceService priceService, AdminService adminService, UserService userService) {
        this.priceService = priceService;
        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping("/admin/prices")
    public ResponseEntity<Object> createPrice(@RequestBody JSONObject priceJSON) {
        Price price = fromJSON(priceJSON);
        price.setLevel(adminService.getAuthenticatable().getSchoolYear().getLevel());

        return ResponseEntity.ok(
                Map.of("message", "Price created successfully", "price", new PriceDTO(priceService.save(price)))
        );
    }

    private Price fromJSON(JSONObject price) {
        Price p = new Price();
        p.setName((String) price.get("name"));
        p.setCoins((Integer) price.get("coins"));
        return p;
    }

    @PutMapping("/admin/prices/{priceId}")
    public ResponseEntity<Object> updatePrice(@PathVariable Long priceId, @RequestBody JSONObject priceJSON) {
        Price price = fromJSON(priceJSON);

        Admin admin = (Admin) adminService.getAuthenticatable();

        if(priceId == 1) {
            if(admin.getRole() != Role.ADMIN) {
                throw new IllegalArgumentException("You don't have permission to update rating price");
            }
        }

        price.setLevel(admin.getSchoolYear().getLevel());
        price.setId(priceId);
        priceService.save(price);

        return ResponseEntity.ok(Map.of("message", "Price updated successfully"));
    }

    @DeleteMapping("/admin/prices/{priceId}")
    public ResponseEntity<Object> deletePrice(@PathVariable Long priceId) {
        if(priceId == 1) {
            throw new IllegalArgumentException("Cannot delete rating price");
        }

        priceService.deletePrice(priceId);

        return ResponseEntity.ok(Map.of("message", "Price deleted successfully"));
    }

    @GetMapping("/admin/prices/{priceId}")
    public ResponseEntity<Object> getPriceAdmin(@PathVariable Long priceId) {
        return ResponseEntity.ok(
                new PriceDTO(priceService.getPriceById(priceId))
        );
    }

    @GetMapping("/prices/{name}")
    public ResponseEntity<Object> getPrice(@PathVariable String name) {
        return ResponseEntity.ok(
                new PriceDTO(priceService.getPrice(name, userService.getAuthenticatable().getSchoolYear().getLevel()))
        );
    }

    @GetMapping("/admin/prices")
    public ResponseEntity<Object> index(@RequestParam Integer page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);

        return ResponseEntity.ok(
                new PaginationDTO<>(
                        priceService.getPrices(
                                pageable,
                                adminService.getAuthenticatable().getSchoolYear().getLevel()
                        ).map(PriceDTO::new)
                )
        );
    }

    @GetMapping("/prices/all")
    public ResponseEntity<Object> getAllPrices() {
        return ResponseEntity.ok(
                priceService.getAllPrices(userService.getAuthenticatable().getSchoolYear().getLevel()).stream().map(PriceDTO::new).toList()
        );
    }

    @GetMapping("/prices")
    public ResponseEntity<Object> getAllPricesForUser() {
        return ResponseEntity.ok(
                priceService.getAllPricesForUser(userService.getAuthenticatable().getSchoolYear().getLevel()).stream().map(PriceDTO::new).toList()
        );
    }
}
