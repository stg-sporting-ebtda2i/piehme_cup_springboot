package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.services.AdminService;
import com.stgsporting.piehmecup.services.PriceService;
import com.stgsporting.piehmecup.services.UserService;
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
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @PostMapping("/admin/prices")
    public ResponseEntity<Object> createPrice(@RequestBody Price price) {
        Long schoolYearId =  adminService.getAuthenticatable().getSchoolYear().getId();
        return ResponseEntity.ok(priceService.save(price, schoolYearId));
    }

    @PutMapping("/admin/prices/{priceId}")
    public ResponseEntity<Object> updatePrice(@PathVariable Long priceId, @RequestBody Price price) {
        if(priceId == 1) {
            Admin admin = (Admin) adminService.getAuthenticatable();
            if(admin.getRole() != Role.ADMIN) {
                throw new IllegalArgumentException("You don't have permission to update rating price");
            }
        }
        Long schoolYearId =  adminService.getAuthenticatable().getSchoolYear().getId();
        price.setId(priceId);
        priceService.save(price, schoolYearId);

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
        return ResponseEntity.ok(priceService.getPriceById(priceId));
    }

    @GetMapping("/prices/{name}")
    public ResponseEntity<Object> getPrice(@PathVariable String name) {
        Long schoolYearId =  userService.getAuthenticatable().getSchoolYear().getId();
        return ResponseEntity.ok(priceService.getPrice(name, schoolYearId));
    }

    @GetMapping("/admin/prices")
    public ResponseEntity<Object> index(@RequestParam Integer page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        Long schoolYearId =  adminService.getAuthenticatable().getSchoolYear().getId();

        return ResponseEntity.ok(
                new PaginationDTO<>(priceService.getPrices(pageable, schoolYearId))
        );
    }

    @GetMapping("/prices/all")
    public ResponseEntity<Object> getAllPrices() {
        Long schoolYearId =  userService.getAuthenticatable().getSchoolYear().getId();
        return ResponseEntity.ok(priceService.getAllPrices(schoolYearId));
    }

    @GetMapping("/prices")
    public ResponseEntity<Object> getAllPricesForUser() {
        Long schoolYearId =  userService.getAuthenticatable().getSchoolYear().getId();
        return ResponseEntity.ok(priceService.getAllPricesForUser(schoolYearId));
    }
}
