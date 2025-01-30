package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Osra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OsraRepository extends JpaRepository<Osra, Long> {
    Optional<Osra> findByName(String name);
}
