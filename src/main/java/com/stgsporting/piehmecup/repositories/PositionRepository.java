package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Modifying
    @Query("update POSITIONS p set p.price = :price where p.name = :name")
    void updatePrice(@Param("name") String name, @Param("price") Integer price);

    Optional<Position> findPositionById(Long id);

    Optional<Position> findPositionByName(String name);
}
