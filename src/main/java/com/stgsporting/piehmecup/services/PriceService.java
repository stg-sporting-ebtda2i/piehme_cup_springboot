package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public void createPrice(String name, Integer price) {
        try {
            Price object = new Price();
            object.setName(name);
            object.setCoins(price);
            priceRepository.save(object);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating price");
        }
    }

    @Transactional
    public void updatePrice(String name, Integer price) {
        try {
            priceRepository.updatePrice(name, price);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating price");
        }
    }

    @Transactional
    public void deletePrice(String name) {
        try {
            priceRepository.deletePricesByName(name);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting price");
        }
    }

    public Price getPrice(String name) {
        try {
            return priceRepository.findPricesByName(name).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching price");
        }
    }

    public List<Price> getAllPrices() {
        try {
            return priceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching prices");
        }
    }
}
