package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.players.PlayerDTO;
import com.stgsporting.piehmecup.dtos.players.PlayerUploadDTO;
import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.exceptions.PlayerNotFoundException;
import com.stgsporting.piehmecup.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final FileService fileService;

    public PlayerService(PlayerRepository playerRepository, FileService fileService) {
        this.playerRepository = playerRepository;
        this.fileService = fileService;
    }

    public void createPlayer(PlayerUploadDTO player) {
        Player newPlayer = dtoToPlayer(player);

        playerRepository.save(newPlayer);
    }

    private Player dtoToPlayer(PlayerUploadDTO player) {
        Player newPlayer = new Player();
        newPlayer.setName(player.getName());
        newPlayer.setPosition(Positions.valueOf(player.getPosition().toUpperCase()));
        newPlayer.setAvailable(player.getAvailable());
        newPlayer.setPrice(player.getPrice());

        String key = fileService.uploadFile(player.getImage(), "/players");

        newPlayer.setImgLink(key);
        newPlayer.setRating(player.getRating());
        return newPlayer;
    }

    public PlayerDTO getPlayerByName(String name) {
        Optional<Player> player = playerRepository.findPlayerByName(name);
        if(player.isPresent())
            return playerToDTO(player.get());


        throw new PlayerNotFoundException("Player with name " + name + " not found");
    }

    public PlayerDTO playerToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setPosition(player.getPosition().toString());
        playerDTO.setAvailable(player.getAvailable());
        playerDTO.setPrice(player.getPrice());

        String url = fileService.generateSignedUrl(player.getImgLink());

        playerDTO.setImageUrl(url);
        playerDTO.setImageKey(player.getImgLink());

        playerDTO.setRating(player.getRating());
        return playerDTO;
    }

    public void updatePlayer(String playerName, PlayerUploadDTO playerDTO) {
        Player player = playerRepository.findPlayerByName(playerName)
                .orElseThrow(() -> new PlayerNotFoundException("Player with name " + playerName + " not found"));

        Player updatedPlayer = dtoToPlayer(playerDTO);

        if(playerDTO.getImage() != null && !player.getImgLink().equals(updatedPlayer.getImgLink()))
            fileService.deleteFile(player.getImgLink());
        else
            updatedPlayer.setImgLink(player.getImgLink());

        updatedPlayer.setId(player.getId());

        playerRepository.save(updatedPlayer);
    }

    public void deletePlayer(String name) {
        Player player = playerRepository.findPlayerByName(name).orElseThrow(
                () -> new PlayerNotFoundException("Player with name " + name + " not found")
        );

        fileService.deleteFile(player.getImgLink());

         playerRepository.delete(player);
    }

    public List<PlayerDTO> getPlayersByPosition(Enum<Positions> position){
        List<Player> players = playerRepository.findPlayersByPosition(position);
        if(players.isEmpty())
            throw new PlayerNotFoundException("No players found with position " + position);

        List<PlayerDTO> playerDTOs = new ArrayList<>();
        for(Player player : players)
            playerDTOs.add(playerToDTO(player));

        return playerDTOs;
    }
}
