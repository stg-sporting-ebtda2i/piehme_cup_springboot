package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.exceptions.SchoolYearNotFound;
import com.stgsporting.piehmecup.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {
    @Autowired
    private LevelRepository levelRepository;

    public Level getLevelByName(String name){
        return levelRepository
                .findByName(name)
                .orElseThrow(
                        () -> new SchoolYearNotFound("Level not found")
                );
    }

    public Level getLevelById(Long id){
        return levelRepository
                .findById(id)
                .orElseThrow(
                        () -> new SchoolYearNotFound("Level not found")
                );
    }

    public Level createLevel(String name) {
        Level level = new Level();
        level.setName(name);
        return levelRepository.save(level);
    }

    public boolean existsLevelById(Long id) {
        return levelRepository.existsById(id);
    }
}
