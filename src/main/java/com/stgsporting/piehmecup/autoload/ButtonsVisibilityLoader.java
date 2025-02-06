package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.repositories.ButtonsVisibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ButtonsVisibilityLoader implements CommandLineRunner {
    @Autowired
    private ButtonsVisibilityRepository buttonsVisibilityRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<ButtonsVisibility> managePlayers = buttonsVisibilityRepository.findByName("Manage Players");
        if (managePlayers.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Players", true, Role.ADMIN));

        Optional<ButtonsVisibility> manageIcons = buttonsVisibilityRepository.findByName("Manage Icons");
        if (manageIcons.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Icons", true, Role.ADMIN));

        Optional<ButtonsVisibility> managePositions = buttonsVisibilityRepository.findByName("Manage Positions");
        if (managePositions.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Positions", true, Role.ADMIN));

        Optional<ButtonsVisibility> manageRatingPrice = buttonsVisibilityRepository.findByName("Manage Rating Price");
        if (manageRatingPrice.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Rating Price", true, Role.ADMIN));

        Optional<ButtonsVisibility> manageQuizzes = buttonsVisibilityRepository.findByName("Manage Quizzes");
        if (manageQuizzes.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Quizzes", true, Role.ADMIN));

        Optional<ButtonsVisibility> manageUsers = buttonsVisibilityRepository.findByName("Manage Users");
        if (manageUsers.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Users", true, Role.ADMIN));

        Optional<ButtonsVisibility> manageStars = buttonsVisibilityRepository.findByName("Manage Stars");
        if (manageStars.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Stars", true, Role.ADMIN));

        Optional<ButtonsVisibility> manageHome = buttonsVisibilityRepository.findByName("Manage Home");
        if (manageHome.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Home", true, Role.ADMIN));

        Optional<ButtonsVisibility> manageAttendance = buttonsVisibilityRepository.findByName("Manage Attendance");
        if (manageAttendance.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Attendance", true, Role.ADMIN));

        Optional<ButtonsVisibility> maintenance = buttonsVisibilityRepository.findByName("Maintenance");
        if (maintenance.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Maintenance", true, Role.ADMIN));

        Optional<ButtonsVisibility> changePicture = buttonsVisibilityRepository.findByName("Change Picture");
        if (changePicture.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Change Picture", true, Role.ADMIN));

        Optional<ButtonsVisibility> mosab2a = buttonsVisibilityRepository.findByName("Mosab2a");
        if (mosab2a.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Mosab2a", true, Role.OSTAZ));

        Optional<ButtonsVisibility> lineup = buttonsVisibilityRepository.findByName("Lineup");
        if (lineup.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Lineup", true, Role.OSTAZ));

        Optional<ButtonsVisibility> store = buttonsVisibilityRepository.findByName("Store");
        if (store.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Store", true, Role.OSTAZ));

        Optional<ButtonsVisibility> card = buttonsVisibilityRepository.findByName("Card");
        if (card.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Card", true, Role.OSTAZ));

        Optional<ButtonsVisibility> leaderboard = buttonsVisibilityRepository.findByName("Leaderboard");
        if (leaderboard.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Leaderboard", true, Role.OSTAZ));

        Optional<ButtonsVisibility> clan = buttonsVisibilityRepository.findByName("Clan");
        if (clan.isEmpty())
            buttonsVisibilityRepository.save(new ButtonsVisibility("Clan", true, Role.OSTAZ));
    }
}
