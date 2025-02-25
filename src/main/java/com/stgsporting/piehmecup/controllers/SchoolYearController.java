package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.SchoolYearService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolYearController {

    private final SchoolYearService schoolYearService;

    public SchoolYearController(SchoolYearService schoolYearService) {
        this.schoolYearService = schoolYearService;
    }

    record SchoolYearDTO(Long id, String name) { }

    @GetMapping("/schoolYears")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok(
                schoolYearService.getAllSchoolYears().stream().map(
                        schoolYear -> new SchoolYearDTO(schoolYear.getId(), schoolYear.getName())
                )
        );
    }

    @GetMapping("/admin/schoolYears")
    public ResponseEntity<Object> indexAdmins() {
        return ResponseEntity.ok(
                schoolYearService.getAllSchoolYears().stream().map(
                        schoolYear -> new SchoolYearDTO(schoolYear.getId(), schoolYear.getName())
                )
        );
    }
}
