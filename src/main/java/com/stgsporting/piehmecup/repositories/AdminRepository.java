package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUser(User user);
}
