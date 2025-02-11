package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.services.SchoolYearService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SchoolYearLoader implements CommandLineRunner {

    private final SchoolYearService schoolYearService;

    SchoolYearLoader(SchoolYearService schoolYearService) {
        this.schoolYearService = schoolYearService;
    }

    @Override
    public void run(String... args) {
        for (long i = 1; i <= 6; i++) {
            if(! schoolYearService.existsSchoolYearById(i)) {
                schoolYearService.createSchoolYear("Junior " + i);
            }
        }
    }
}
