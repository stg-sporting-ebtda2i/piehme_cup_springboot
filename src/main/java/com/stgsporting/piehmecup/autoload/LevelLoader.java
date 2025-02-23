package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.services.LevelService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class LevelLoader implements CommandLineRunner {

    private final LevelService schoolYearService;

    LevelLoader(LevelService schoolYearService) {
        this.schoolYearService = schoolYearService;
    }

    @Override
    public void run(String... args) {

        if(! schoolYearService.existsLevelById(1L)) {
            schoolYearService.createLevel("Ebteda2i");
        }

        if(! schoolYearService.existsLevelById(2L)) {
            schoolYearService.createLevel("E3dady");
        }

    }
}
