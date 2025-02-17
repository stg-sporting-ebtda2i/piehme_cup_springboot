package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ButtonsVisibilityRepository extends JpaRepository<ButtonsVisibility, Long> {
    List<ButtonsVisibility> findButtonsVisibilityByRole(Role role);

    @Query("SELECT bv FROM BUTTONS_VISIBILITY bv WHERE bv.visible = true")
    List<ButtonsVisibility> findAllVisible();

    Optional<ButtonsVisibility> findByName(String name);
}
