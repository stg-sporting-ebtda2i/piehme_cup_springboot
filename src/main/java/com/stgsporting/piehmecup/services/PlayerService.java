package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.PlayerDTO;
import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.exceptions.PlayerNotFoundException;
import com.stgsporting.piehmecup.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public void createPlayer(PlayerDTO player) {
        try{
            Player newPlayer = dtoToPlayer(player);

            playerRepository.save(newPlayer);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Player cannot be null");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving player");
        }
    }

    private static Player dtoToPlayer(PlayerDTO player) {
        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setPosition(Positions.valueOf(player.getPosition().toUpperCase()));
        newPlayer.setAvailable(player.getAvailable());
        newPlayer.setPrice(player.getPrice());
        newPlayer.setImgLink(player.getImgLink());
        newPlayer.setRating(player.getRating());
        return newPlayer;
    }

    public PlayerDTO getPlayerByName(String name) {
        Optional<Player> player = playerRepository.findPlayerByName(name);
        if(player.isPresent())
            return playerToDTO(player.get());


        throw new PlayerNotFoundException("Player with name " + name + " not found");
    }

    private static PlayerDTO playerToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setPosition(player.getPosition().toString());
        playerDTO.setAvailable(player.getAvailable());
        playerDTO.setPrice(player.getPrice());
        playerDTO.setImgLink(player.getImgLink());
        playerDTO.setRating(player.getRating());
        return playerDTO;
    }

    public void updatePlayer(String playerName,PlayerDTO player) {
        Optional<Player> playerToUpdate = playerRepository.findPlayerByName(playerName);
        if(playerToUpdate.isPresent()){
            Player updatedPlayer = dtoToPlayer(player);
            updatedPlayer.setId(playerToUpdate.get().getId());
            playerRepository.save(updatedPlayer);
        }

        else
            throw new PlayerNotFoundException("Player with name " + playerName + " not found");
    }

    public void deletePlayer(String name) {
        Optional<Player> player = playerRepository.findPlayerByName(name);
        if(player.isPresent())
            playerRepository.delete(player.get());

        else
            throw new PlayerNotFoundException("Player with name " + name + " not found");
    }

    public List<Player> getPlayersByPosition(Enum<Positions> position){
        List<Player> players = playerRepository.findPlayersByPosition(position);
        if(players.isEmpty())
            throw new PlayerNotFoundException("No players found with position " + position);

        return players;
    }
}
