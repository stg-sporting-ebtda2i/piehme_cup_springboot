package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.players.PlayerDTO;
import com.stgsporting.piehmecup.dtos.players.PlayerUploadDTO;
import com.stgsporting.piehmecup.entities.Player;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.exceptions.PlayerNotFoundException;
import com.stgsporting.piehmecup.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final FileService fileService;
    private final PositionService positionService;

    public PlayerService(PlayerRepository playerRepository, FileService fileService, PositionService positionService) {
        this.playerRepository = playerRepository;
        this.fileService = fileService;
        this.positionService = positionService;
    }

    public void createPlayer(PlayerUploadDTO player) {
        Player newPlayer = dtoToPlayer(player);

        playerRepository.save(newPlayer);
    }

    private Player dtoToPlayer(PlayerUploadDTO player) {
        Player newPlayer = new Player();
        newPlayer.setName(player.getName());

        newPlayer.setPosition(positionService.getPositionByName(player.getPosition()));

        newPlayer.setAvailable(player.getAvailable());
        newPlayer.setPrice(player.getPrice());

        String key = fileService.uploadFile(player.getImage(), "/players");

        newPlayer.setImgLink(key);
        newPlayer.setRating(player.getRating());
        return newPlayer;
    }

    public PlayerDTO getPlayerByName(String name) {
        Player player = playerRepository.findPlayerByName(name).orElseThrow(
                () -> new PlayerNotFoundException("Player with name " + name + " not found")
        );

        return playerToDTO(player);
    }

    public PlayerDTO getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(PlayerNotFoundException::new);

        return playerToDTO(player);
    }

    public Page<PlayerDTO> getPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable).map(this::playerToDTO);
    }

    public PlayerDTO playerToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setPosition(player.getPosition().getName());
        playerDTO.setAvailable(player.getAvailable());
        playerDTO.setPrice(player.getPrice());

        String url = fileService.generateSignedUrl(player.getImgLink());

        playerDTO.setImageUrl(url);
        playerDTO.setImageKey(player.getImgLink());

        playerDTO.setRating(player.getRating());
        return playerDTO;
    }

    public void updatePlayer(Long playerId, PlayerUploadDTO playerDTO) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

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

    public List<PlayerDTO> getPlayersByPosition(Positions position){
        List<Player> players = playerRepository.findPlayersByPosition(position);

        List<PlayerDTO> playerDTOs = new ArrayList<>();
        for(Player player : players)
            playerDTOs.add(playerToDTO(player));

        return playerDTOs;
    }
}
