package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.enums.Positions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Optional<Player> findPlayerByName(String name);
    @Query("SELECT p FROM PLAYERS p WHERE p.position = ?1 AND p.available = true ORDER BY p.rating DESC")
    List<Player> findPlayersByPositionId(Position position);

    @Query("SELECT p FROM PLAYERS p WHERE p.position = ?1 AND p.level=?2 AND p.available = true ORDER BY p.rating DESC")
    List<Player> findPlayersByPositionAndLevel(Position position, Level level);

    Page<Player> findPlayersByLevel(Pageable pageable, Level level);
}
