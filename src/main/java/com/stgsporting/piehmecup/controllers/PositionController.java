package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/positions")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @PutMapping("/update/{name}/{price}")
    public void updatePrice(@PathVariable String name, @PathVariable Integer price){
        positionService.updatePrice(name, price);
    }
}
