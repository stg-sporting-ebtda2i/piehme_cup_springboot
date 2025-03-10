package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InsightsRepository extends JpaRepository<User, Long> {
    @Query("SELECT COUNT(op.id) as count, p.name " +
            "FROM  User.players op " +
            "JOIN PLAYERS p ON op.id = p.id " +
            "WHERE p.level.id = 1 " +
            "GROUP BY op.id, p.name " +
            "ORDER BY count DESC " +
            "LIMIT 10")
    void getBestSellingPlayers(Long levelId);
}
