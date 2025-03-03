package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.PositionDTO;
import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.PositionNotFoundException;
import com.stgsporting.piehmecup.exceptions.UnownedPositionException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnedPositionsService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PositionRepository positionRepository;
    private final WalletService walletService;

    public OwnedPositionsService(UserRepository userRepository, UserService userService, PositionRepository positionRepository, WalletService walletService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.positionRepository = positionRepository;
        this.walletService = walletService;
    }

    public List<PositionDTO> getOwnedPositions() {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findUserByIdWithPositions(userId)
                .orElseThrow(UserNotFoundException::new);

        List<PositionDTO> positionDTOS = new ArrayList<>();
        for (Position position : user.getPositions())
            positionDTOS.add(PositionService.positionToDTO(position));

        return positionDTOS;
    }

    @Transactional
    public void addPositionToUser(Long positionId) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findUserByIdWithPositions(userId)
                .orElseThrow(UserNotFoundException::new);

        Position position = positionRepository.findById(positionId)
                .orElseThrow(PositionNotFoundException::new);

        if (user.doesntOwn(position)) {
            walletService.debit(user, position.getPrice(), "Position purchase: " + position.getName());

            user.getPositions().add(position);
            user.setSelectedPosition(position);
            userRepository.save(user);
        }
    }

    @Transactional
    public void removePositionFromUser(Long positionId) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findUserByIdWithPositions(userId)
                .orElseThrow(UserNotFoundException::new);
        Position position = positionRepository.findById(positionId)
                .orElseThrow(PositionNotFoundException::new);

        if(position.isDefault() && user.doesntOwn(position)) {
            user.addPosition(position);
            userRepository.save(user);
            throw new UnownedPositionException("You can't sell " + Position.defaultPosition + " position");
        }

        if (position.isDefault()) {
            throw new UnownedPositionException("You can't sell " + Position.defaultPosition + " position");
        }

        if (user.doesntOwn(position)) {
            throw new UnownedPositionException();
        }

        walletService.credit(user, position.getPrice(), "Position sale: " + position.getName());

        user.getPositions().remove(position);

        Position defaultPosition = positionRepository.findPositionByName(Position.defaultPosition)
                .orElseThrow(PositionNotFoundException::new);

        user.setSelectedPosition(defaultPosition);
        userRepository.save(user);
    }
}
