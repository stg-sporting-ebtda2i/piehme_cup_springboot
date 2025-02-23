package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByName(String name);
}
