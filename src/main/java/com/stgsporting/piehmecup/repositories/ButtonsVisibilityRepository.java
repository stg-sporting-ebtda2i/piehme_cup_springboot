package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ButtonsVisibilityRepository extends JpaRepository<ButtonsVisibility, Long> {
    List<ButtonsVisibility> findButtonsVisibilityByUserRole(Enum<Role> userRole);

    Optional<ButtonsVisibility> findByName(String name);

    @Modifying
    @Query("update BUTTONS_VISIBILITY b set b.visible = :visible where b.name = :name")
    void changeVisibilityByName(String name, Boolean visible);
}
