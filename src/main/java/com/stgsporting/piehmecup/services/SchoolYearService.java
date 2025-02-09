package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.exceptions.SchoolYearNotFound;
import com.stgsporting.piehmecup.repositories.SchoolYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolYearService {
    @Autowired
    private SchoolYearRepository schoolYearRepository;

    public SchoolYear getShoolYearByName(String name){
        Optional<SchoolYear> schoolYear = schoolYearRepository.findByName(name);
        if(schoolYear.isEmpty())
            throw new SchoolYearNotFound("School Year not found");
        return schoolYear.get();
    }
}
