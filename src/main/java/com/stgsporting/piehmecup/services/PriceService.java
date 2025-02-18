package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.exceptions.PriceNotFoundException;
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
        Price object = new Price();
        object.setName(name);
        object.setCoins(price);
        priceRepository.save(object);
    }

    @Transactional
    public void updatePrice(String name, Integer price) {
        priceRepository.updatePrice(name, price);
    }

    @Transactional
    public void deletePrice(String name) {
        priceRepository.deletePricesByName(name);
    }

    public Price getPrice(String name) {
        return priceRepository.findPricesByName(name)
                .orElseThrow(PriceNotFoundException::new);
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    public List<Price> getAllPricesForUser() {
        return priceRepository.findAllExcept(1L);
    }
}
