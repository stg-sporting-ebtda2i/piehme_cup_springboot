package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Icon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IconRepository extends JpaRepository<Icon, Long> {
    Optional<Icon> findIconByName(String name);
    List<Icon> findIconsByAvailable(Boolean available);
}
