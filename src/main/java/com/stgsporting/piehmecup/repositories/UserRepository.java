package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);
    Optional<User> findUsersByUsername(String username);

    @Query("SELECT u.players FROM User u WHERE u.id = :userId")
    List<Player> findPlayersByUserId(@Param("userId") Long userId);

    @Query("SELECT u.icons FROM User u WHERE u.id = :userId")
    List<Icon> findIconsByUserId(@Param("userId") Long userId);

    @Query("SELECT u.positions FROM User u WHERE u.id = :userId")
    List<Position> findPositionsByUserId(@Param("userId") Long userId);

    User getUserById(Long id);

    @Query("SELECT u FROM User u WHERE u.schoolYear = :schoolYear ORDER BY u.lineupRating desc")
    List<User> findUsersBySchoolYear(SchoolYear schoolYear);
}
