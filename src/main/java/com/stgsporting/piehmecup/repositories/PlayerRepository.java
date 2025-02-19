package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.enums.Positions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Optional<Player> findPlayerByName(String name);
    @Query("SELECT p FROM PLAYERS p WHERE p.position = ?1 ORDER BY p.rating DESC")
    List<Player> findPlayersByPositionId(Position position);
}
