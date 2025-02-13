package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.PlayerDTO;
import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.InsufficientCoinsException;
import com.stgsporting.piehmecup.exceptions.PlayerAlreadyPurchasedException;
import com.stgsporting.piehmecup.exceptions.PlayerNotFoundException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.PlayerRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnedPlayersService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private WalletService walletService;

    public List<PlayerDTO> getLineup(){
        try {
            Long userId = userService.getAuthenticatableId();
            List<Player> players = userRepository.findPlayersByUserId(userId);
            List<PlayerDTO> playerDTOS = new ArrayList<>();
            for(Player player : players)
                playerDTOS.add(PlayerService.playerToDTO(player));

            return playerDTOS;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<PlayerDTO> getLineup(Long userId){
        try {
            List<Player> players = userRepository.findPlayersByUserId(userId);
            List<PlayerDTO> playerDTOS = new ArrayList<>();
            for(Player player : players)
                playerDTOS.add(PlayerService.playerToDTO(player));

            return playerDTOS;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    public void addPlayerToUser(Long playerId) {
        try{
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Player player = playerRepository.findById(playerId)
                    .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

            if (!user.getPlayers().contains(player)) {
                walletService.debit(user, player.getPrice(), "Player purchase: " + player.getId());

                user.getPlayers().add(player);
                user.setLineupRating(user.getLineupRating() + player.getRating());
                userRepository.save(user);
            }
            else
                throw new PlayerAlreadyPurchasedException("Player already purchased");

        } catch (UserNotFoundException | PlayerNotFoundException | InsufficientCoinsException | PlayerAlreadyPurchasedException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while adding player to user");
        }
    }

    @Transactional
    public void removePlayerFromUser(Long playerId) {
        try{
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Player player = playerRepository.findById(playerId)
                    .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

            if (user.getPlayers().contains(player)) {
                walletService.credit(user, player.getPrice(), "Player sale: " + player.getId());

                user.getPlayers().remove(player);
                user.setLineupRating(user.getLineupRating() - player.getRating());
                userRepository.save(user);
            }
            else
                throw new PlayerNotFoundException("User does not own player");
        } catch (UserNotFoundException | PlayerNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while removing player from user");
        }
    }
}
