package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Icon;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IconRepository extends JpaRepository<Icon, Long> {
    Optional<Icon> findIconByName(String name);
    List<Icon> findIconsByAvailable(Boolean available);
    Optional<Icon> findIconById(Long id);

    @Query("SELECT i FROM ICONS i ORDER BY i.price DESC")
    Page<Icon> findAllPaginated(Pageable pageable);

    @NotNull
    @Query("SELECT i FROM ICONS i ORDER BY i.price DESC")
    List<Icon> findAll();
}
