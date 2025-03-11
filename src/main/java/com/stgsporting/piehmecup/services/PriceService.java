package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.exceptions.PriceNotFoundException;
import com.stgsporting.piehmecup.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public void updatePrice(String name, Integer price, Long levelId) {
        priceRepository.updatePrice(name, price, levelId);
    }

    @Transactional
    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }

    public Price getPrice(String name, Level level) {
        return priceRepository.findPricesByNameAndLevel(name, level.getId())
                .orElseThrow(PriceNotFoundException::new);
    }

    public Page<Price> getPrices(Pageable pageable, Level level) {
        return priceRepository.findAll(pageable, level.getId());
    }

    public List<Price> getAllPrices(Level level) {
        return priceRepository.findAllByLevelId(level.getId());
    }

    public List<Price> getAllPricesForUser(Level level) {
        return priceRepository.findAllExcept("Rating Price", level.getId());
    }

    public Price save(Price price) {
        Optional<Price> existingPrice = priceRepository.findPricesByNameAndLevel(price.getName(), price.getLevel().getId());

        if (existingPrice.isPresent() && !existingPrice.get().getId().equals(price.getId())) {
            throw new IllegalArgumentException("Price with name " + price.getName() + " already exists");
        }

        return priceRepository.save(price);
    }

    public Price getPriceById(Long priceId) {
        return priceRepository.findById(priceId)
                .orElseThrow(PriceNotFoundException::new);
    }
}
