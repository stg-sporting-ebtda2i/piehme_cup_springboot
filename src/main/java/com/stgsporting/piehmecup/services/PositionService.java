package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.PositionDTO;
import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.exceptions.PositionNotFoundException;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    static PositionDTO positionToDTO(Position position) {
        return new PositionDTO(position.getId(), position.getName(), position.getPrice().toString());
    }

    public List<PositionDTO> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        return positions.stream().map(PositionService::positionToDTO).toList();
    }

    @Transactional
    public void updatePrice(String name, Integer price) {
        positionRepository.updatePrice(name, price);
    }

    public Position getPositionByName(String position) {
        return positionRepository.findPositionByName(position)
                .orElseThrow(PositionNotFoundException::new);
    }
}
