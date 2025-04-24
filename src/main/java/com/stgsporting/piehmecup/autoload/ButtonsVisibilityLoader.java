package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.repositories.ButtonsVisibilityRepository;
import com.stgsporting.piehmecup.repositories.SchoolYearRepository;
import com.stgsporting.piehmecup.services.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ButtonsVisibilityLoader implements CommandLineRunner {
    @Autowired
    private ButtonsVisibilityRepository buttonsVisibilityRepository;
    @Autowired
    private SchoolYearRepository schoolYearRepository;
    @Override
    public void run(String... args) throws Exception {
        List<SchoolYear> schoolYears = schoolYearRepository.findAll();

        for(SchoolYear schoolYear : schoolYears) {
            Level level = schoolYear.getLevel();

            Optional<ButtonsVisibility> managePlayers = buttonsVisibilityRepository.findByNameAndLevel("Manage Players", level);
            if (managePlayers.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Players", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> manageIcons = buttonsVisibilityRepository.findByNameAndLevel("Manage Icons", level);
            if (manageIcons.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Icons", true, Role.ADMIN ,level));

            Optional<ButtonsVisibility> managePositions = buttonsVisibilityRepository.findByNameAndLevel("Manage Positions", level);
            if (managePositions.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Positions", true, Role.ADMIN ,level));

            Optional<ButtonsVisibility> manageRatingPrice = buttonsVisibilityRepository.findByNameAndLevel("Manage Rating Price", level);
            if (manageRatingPrice.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Rating Price", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> manageQuizzes = buttonsVisibilityRepository.findByNameAndLevel("Manage Quizzes", level);
            if (manageQuizzes.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Quizzes", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> manageUsers = buttonsVisibilityRepository.findByNameAndLevel("Manage Users", level);
            if (manageUsers.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Users", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> manageStars = buttonsVisibilityRepository.findByNameAndLevel("Manage Stars", level);
            if (manageStars.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Stars", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> manageHome = buttonsVisibilityRepository.findByNameAndLevel("Manage Home", level);
            if (manageHome.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Home", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> manageAttendance = buttonsVisibilityRepository.findByNameAndLevel("Manage Attendance", level);
            if (manageAttendance.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Manage Attendance", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> maintenance = buttonsVisibilityRepository.findByNameAndLevel("Maintenance", level);
            if (maintenance.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Maintenance", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> changePicture = buttonsVisibilityRepository.findByNameAndLevel("Change Picture", level);
            if (changePicture.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Change Picture", true, Role.ADMIN, level));

            Optional<ButtonsVisibility> mosab2a = buttonsVisibilityRepository.findByNameAndLevel("Mosab2a", level);
            if (mosab2a.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Mosab2a", true, Role.OSTAZ, level));

            Optional<ButtonsVisibility> lineup = buttonsVisibilityRepository.findByNameAndLevel("Lineup", level);
            if (lineup.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Lineup", true, Role.OSTAZ, level));

            Optional<ButtonsVisibility> store = buttonsVisibilityRepository.findByNameAndLevel("Store", level);
            if (store.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Store", true, Role.OSTAZ, level));

            Optional<ButtonsVisibility> card = buttonsVisibilityRepository.findByNameAndLevel("Card", level);
            if (card.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Card", true, Role.OSTAZ, level));

            Optional<ButtonsVisibility> leaderboard = buttonsVisibilityRepository.findByNameAndLevel("Leaderboard", level);
            if (leaderboard.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Leaderboard", false, Role.OSTAZ, level));

            Optional<ButtonsVisibility> clan = buttonsVisibilityRepository.findByNameAndLevel("Clan", level);
            if (clan.isEmpty())
                buttonsVisibilityRepository.save(new ButtonsVisibility("Clan", false, Role.OSTAZ, level));
        }
    }
}
