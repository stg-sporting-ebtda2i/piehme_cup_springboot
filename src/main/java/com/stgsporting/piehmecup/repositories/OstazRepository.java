package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Ostaz;
import com.stgsporting.piehmecup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OstazRepository extends JpaRepository<Ostaz, Long> {
    Optional<Ostaz> findByUser(User user);
}
