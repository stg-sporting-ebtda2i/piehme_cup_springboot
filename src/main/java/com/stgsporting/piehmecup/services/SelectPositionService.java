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
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Position position = positionRepository.findPositionById(positionId).orElseThrow(PositionNotFoundException::new);

        if (!user.getPositions().contains(position)) {
            throw new UnownedPositionException();
        }

        user.setSelectedPosition(position);
        userRepository.save(user);
    }

    public PositionDTO getSelectedPosition() {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Position position = user.getSelectedPosition();
        if (position == null)
            throw new PositionNotFoundException("No selected position found");

        return new PositionDTO(position.getId(), position.getName(), position.getPrice().toString());
    }
}
