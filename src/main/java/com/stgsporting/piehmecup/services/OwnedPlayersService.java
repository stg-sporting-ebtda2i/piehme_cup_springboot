package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.players.PlayerDTO;
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
    @Autowired
    private PlayerService playerService;

    public List<PlayerDTO> getLineup(){
        try {
            Long userId = userService.getAuthenticatableId();
            List<Player> players = userRepository.findPlayersByUserId(userId);
            List<PlayerDTO> playerDTOS = new ArrayList<>();
            for(Player player : players)
                playerDTOS.add(playerService.playerToDTO(player));

            return playerDTOS;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
    }

    public List<PlayerDTO> getLineup(Long userId){
        try {
            List<Player> players = userRepository.findPlayersByUserId(userId);
            List<PlayerDTO> playerDTOS = new ArrayList<>();
            for(Player player : players)
                playerDTOS.add(playerService.playerToDTO(player));

            return playerDTOS;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    public void addPlayerToUser(Long playerId) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Player player = playerRepository.findById(playerId).orElseThrow(PlayerNotFoundException::new);

        if (user.getPlayers().contains(player)) {
            throw new PlayerAlreadyPurchasedException();
        }
        if (!player.getPosition().getName().equals("CM") && !player.getPosition().getName().equals("CB")) {
            for(Player p : user.getPlayers()) {
                if(p.getPosition().equals(player.getPosition())) {
                    throw new PlayerAlreadyPurchasedException("Player of this position already purchased");
                }
                if (p.getName().equals(player.getName())) {
                    throw new PlayerAlreadyPurchasedException("Cannot purchase same player twice");
                }
            }
        } else {
            int count = 0;
            for(Player p : user.getPlayers()) {
                if(p.getPosition().equals(player.getPosition())) {
                    count++;
                }
            }
            if(count >= 2) {
                throw new PlayerAlreadyPurchasedException("Cannot purchase more than 2 players of this position");
            }
        }


        walletService.debit(user, player.getPrice(), "Player purchase: " + player.getId());

        user.getPlayers().add(player);
        userRepository.save(user);

    }

    @Transactional
    public void removePlayerFromUser(Long playerId) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

        if (!user.getPlayers().contains(player)) {
            throw new PlayerNotFoundException("User does not own player");
        }

        walletService.credit(user, player.getPrice(), "Player sale: " + player.getId());

        user.getPlayers().remove(player);
        userRepository.save(user);
    }
}
