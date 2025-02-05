package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.repositories.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    @Transactional
    public void updatePrice(String name, Integer price) {
        try{
            positionRepository.updatePrice(name, price);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating price");
        }
    }
}
