package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.entities.Walad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaladRepository extends JpaRepository<Walad, Long> {
    Optional<Walad> findByUser(User user);
}
