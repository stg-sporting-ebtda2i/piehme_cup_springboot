package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.InsightsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insights")
public class InsightsController {
    private final InsightsService insightsService;

    public InsightsController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping("/top-players")
    public ResponseEntity<Object> getBestSellingPlayers(@RequestParam Long levelId) {
        return ResponseEntity.ok(insightsService.findBestSeller(levelId));
    }
}
