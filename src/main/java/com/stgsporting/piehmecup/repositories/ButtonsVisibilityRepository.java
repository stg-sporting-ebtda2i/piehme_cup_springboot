package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ButtonsVisibilityRepository extends JpaRepository<ButtonsVisibility, Long> {
    List<ButtonsVisibility> findButtonsVisibilityByRole(Role role);

    Optional<ButtonsVisibility> findByName(String name);
}
