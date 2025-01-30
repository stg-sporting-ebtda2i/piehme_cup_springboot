package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(long id);
    Optional<User> findUsersByUsername(String username);
}
