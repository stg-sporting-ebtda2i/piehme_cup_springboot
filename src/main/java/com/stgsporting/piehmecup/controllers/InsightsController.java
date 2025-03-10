package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.InsightsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insights")
public class InsightsController {
    private final InsightsService insightsService;

    public InsightsController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping("/top-players")
    public void getBestSellingPlayers(@RequestParam Long levelId) {
        insightsService.findBestSeller(levelId);
    }
}
