package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.PlayerDTO;
import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.exceptions.PlayerAlreadyPurchasedException;
import com.stgsporting.piehmecup.exceptions.PlayerNotFoundException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.PlayerRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class OwnedPlayersServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    private Long userId;
    private Long playerId;

    @BeforeEach
    void setup() {
        // Create a user
        User user = new User();
        user.setCoins(100);
        user.setLineupRating(0.0);
        user.setPlayers(new ArrayList<>());
        user = userRepository.save(user);

        // Create a player
        Player player = new Player();
        player.setPrice(100);
        player.setRating(10);
        player = playerRepository.save(player);

        // Assign player to user
        user.getPlayers().add(player);
        user.setLineupRating(Double.valueOf(player.getRating()));
        userRepository.save(user);

        // Store IDs
        userId = user.getId();
        playerId = player.getId();
    }

    @Test
    @Transactional
    @Rollback
    void testConcurrentPlayerRemoval() throws InterruptedException, ExecutionException {
        // Run two concurrent transactions
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            try {
                removePlayer(playerId);
            } catch (Exception e) {
                System.out.println("Task 1 failed: " + e.getMessage());
            }
        });

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            try {
                removePlayer(playerId);
            } catch (Exception e) {
                System.out.println("Task 2 failed: " + e.getMessage());
            }
        });

        // Wait for both tasks to finish
        CompletableFuture.allOf(task1, task2).get();

        // Verify that the player was removed only once
        User user = userRepository.findById(userId).orElseThrow();
        Player player = playerRepository.findById(playerId).orElse(null);

        assertFalse(user.getPlayers().contains(player), "Player should be removed from the user");
        assertEquals(0, user.getLineupRating(), "Lineup rating should be updated correctly");

        // Verify that the user's wallet was credited only once
        assertEquals(200, user.getCoins(), "User should receive only one payment");
    }

    @Transactional
    protected void removePlayer(Long playerId) {
        Long authUserId = userService.getAuthenticatableId(); // Simulate user authentication
        User user = userRepository.findById(authUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        synchronized (this) { // Prevents race condition
            if (user.getPlayers().contains(player)) {
                walletService.credit(user, player.getPrice(), "Player sale: " + player.getId());
                user.getPlayers().remove(player);
                user.setLineupRating(user.getLineupRating() - player.getRating());
                userRepository.save(user);
            }
        }
    }
}