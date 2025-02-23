package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.services.LevelService;
import com.stgsporting.piehmecup.services.SchoolYearService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SchoolYearLoader implements CommandLineRunner {

    private final SchoolYearService schoolYearService;
    private final LevelService levelService;

    SchoolYearLoader(SchoolYearService schoolYearService, LevelService levelService) {
        this.schoolYearService = schoolYearService;
        this.levelService = levelService;
    }

    @Override
    public void run(String... args) {
        Level ebteda2i = levelService.getLevelById(1L);
        Level e3dady = levelService.getLevelById(2L);


        for (long i = 1; i <= 6; i++) {
            if(! schoolYearService.existsSchoolYearById(i)) {
                schoolYearService.createSchoolYear("Junior " + i, ebteda2i);
            }
        }

        if(! schoolYearService.existsSchoolYearById(7L)) {
            schoolYearService.createSchoolYear("E3dady", e3dady);
        }

    }
}
