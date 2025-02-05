package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.enums.Positions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Optional<Player> findPlayerByName(String name);
    List<Player> findPlayersByPosition(Enum<Positions> position);
}
