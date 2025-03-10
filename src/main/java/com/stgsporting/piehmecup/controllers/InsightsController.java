package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.InsightsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insights")
public class InsightsController {
    private final InsightsService insightsService;

    public InsightsController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping("/top-players/{levelId}")
    public void getBestSellingPlayers(@PathVariable Long levelId) {
        insightsService.findBestSeller(levelId);
    }
}
