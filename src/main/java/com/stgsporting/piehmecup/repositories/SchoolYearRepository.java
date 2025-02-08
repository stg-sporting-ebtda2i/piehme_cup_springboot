package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {
    Optional<SchoolYear> findByName(String name);
}
