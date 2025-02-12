package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.PositionDTO;
import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.InsufficientCoinsException;
import com.stgsporting.piehmecup.exceptions.PositionNotFoundException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnedPositionsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private WalletService walletService;

    public List<PositionDTO> getOwnedPositions(){
        try {
            Long userId = userService.getAuthenticatableId();
            List<Position> positions = userRepository.findPositionsByUserId(userId);
            List<PositionDTO> positionDTOS = new ArrayList<>();
            for(Position position : positions)
                positionDTOS.add(PositionService.positionToDTO(position));

            return positionDTOS;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    public void addPositionToUser(Long positionId) {
        try{
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Position position = positionRepository.findById(positionId)
                    .orElseThrow(() -> new PositionNotFoundException("Position not found"));

            if (!user.getPositions().contains(position)) {
                walletService.debit(user, position.getPrice(), "Position purchase: " + position.getName());

                user.getPositions().add(position);
                userRepository.save(user);
            }
        } catch (UserNotFoundException | PositionNotFoundException | InsufficientCoinsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error adding position to user");
        }
    }

    @Transactional
    public void removePositionFromUser(Long positionId) {
        try{
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Position position = positionRepository.findById(positionId)
                    .orElseThrow(() -> new PositionNotFoundException("Position not found"));

            if (user.getPositions().contains(position)) {
                walletService.credit(user, position.getPrice(), "Position sale: " + position.getName());

                user.getPositions().remove(position);
                userRepository.save(user);
            }
        } catch (UserNotFoundException | PositionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error removing position from user");
        }
    }
}
