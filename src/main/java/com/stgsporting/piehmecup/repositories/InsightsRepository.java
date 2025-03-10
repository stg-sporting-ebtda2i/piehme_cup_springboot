package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.dtos.insights.BestSellerDTO;
import com.stgsporting.piehmecup.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsightsRepository extends JpaRepository<Player, Long> {

    @Query("SELECT new com.stgsporting.piehmecup.dtos.insights.BestSellerDTO(COUNT(p.id), p.name) " +
            "FROM PLAYERS p " +
            "JOIN p.user u " +
            "WHERE p.level.id = :levelId " +
            "GROUP BY p.id, p.name " +
            "ORDER BY COUNT(p.id) DESC")
    List<BestSellerDTO> findBestSeller(@Param("levelId") Long levelId);
}