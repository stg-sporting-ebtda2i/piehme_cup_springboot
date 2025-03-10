package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByName(String name);

    @Query("select l from LEVELS l order by l.id")
    List<Level> findAllOrderById();
}
