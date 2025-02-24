package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.exceptions.SchoolYearNotFound;
import com.stgsporting.piehmecup.repositories.SchoolYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolYearService {
    @Autowired
    private SchoolYearRepository schoolYearRepository;

    public SchoolYear getShoolYearByName(String name){
        return schoolYearRepository
                .findByName(name)
                .orElseThrow(
                        () -> new SchoolYearNotFound("School Year not found")
                );
    }

    public SchoolYear getShoolYearById(Long id){
        return schoolYearRepository
                .findById(id)
                .orElseThrow(
                        () -> new SchoolYearNotFound("School Year not found")
                );
    }

    public SchoolYear createSchoolYear(String name, Level level) {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setName(name);
        schoolYear.setLevel(level);
        return schoolYearRepository.save(schoolYear);
    }

    public boolean existsSchoolYearById(Long id) {
        return schoolYearRepository.existsById(id);
    }

    public List<SchoolYear> getAllSchoolYears() {
        return schoolYearRepository.findAll();
    }
}
