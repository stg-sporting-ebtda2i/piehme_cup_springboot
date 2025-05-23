package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ButtonsVisibilityRepository extends JpaRepository<ButtonsVisibility, Long> {
    List<ButtonsVisibility> findButtonsVisibilityByRole(Role role);
    List<ButtonsVisibility> findButtonsVisibilityByRoleAndLevel(Role role, Level level);

    @Query("SELECT bv FROM BUTTONS_VISIBILITY bv WHERE bv.visible = true")
    List<ButtonsVisibility> findAllVisible();

    @Query("SELECT bv FROM BUTTONS_VISIBILITY bv WHERE bv.visible = true AND bv.level = ?1")
    List<ButtonsVisibility> findAllVisible(Level level);

    Optional<ButtonsVisibility> findByName(String name);
    Optional<ButtonsVisibility> findByNameAndLevel(String name, Level level);

    List<ButtonsVisibility> findAllByLevel(Level level);
}
