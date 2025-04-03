package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.services.CardRatingService;
import com.stgsporting.piehmecup.services.SchoolYearService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/reset-cards")
public class ResetCardsController {
    private final CardRatingService cardRatingService;
    private final SchoolYearService schoolYearService;

    public ResetCardsController(CardRatingService cardRatingService, SchoolYearService schoolYearService) {
        this.schoolYearService = schoolYearService;
        this.cardRatingService = cardRatingService;
    }

    @PatchMapping("/{schoolYearId}")
    public void resetCards(@PathVariable Long schoolYearId) {
        SchoolYear schoolYear = schoolYearService.getShoolYearById(schoolYearId);
        cardRatingService.resetAllCards(schoolYear);
    }
}
