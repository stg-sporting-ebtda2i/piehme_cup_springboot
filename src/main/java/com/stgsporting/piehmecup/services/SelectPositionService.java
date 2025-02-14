package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.PositionDTO;
import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.PositionNotFoundException;
import com.stgsporting.piehmecup.exceptions.UnownedPositionException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectPositionService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    public void selectPosition(Long positionId) {
        try {
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Position position = positionRepository.findPositionById(positionId)
                    .orElseThrow(() -> new PositionNotFoundException("Position not found"));

            if (user.getPositions().contains(position)) {
                user.setSelectedPosition(position);
                userRepository.save(user);
            }
            else
                throw new UnownedPositionException("Position selected is not owned by the user");

        } catch (UserNotFoundException | PositionNotFoundException | UnownedPositionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while selecting position");
        }
    }

    public PositionDTO getSelectedPosition() {
        try {
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Position position = user.getSelectedPosition();
            if (position == null)
                throw new PositionNotFoundException("No selected position found");

            return new PositionDTO(position.getId(), position.getName(), position.getPrice().toString());
        } catch (UserNotFoundException | PositionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while selecting position");
        }
    }
}
