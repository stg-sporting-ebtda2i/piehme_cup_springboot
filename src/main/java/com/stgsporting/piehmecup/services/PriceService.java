package com.stgsporting.piehmecup.services;

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
    public void updatePrice(String name, Integer price, Long schoolYearId) {
        priceRepository.updatePrice(name, price, schoolYearId);
    }

    @Transactional
    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }

    public Price getPrice(String name,Long schoolYearId) {
        return priceRepository.findPricesByNameAndSchoolYear(name, schoolYearId)
                .orElseThrow(PriceNotFoundException::new);
    }

    public Page<Price> getPrices(Pageable pageable, Long schoolYearId) {
        return priceRepository.findAll(pageable, schoolYearId);
    }

    public List<Price> getAllPrices(Long schoolYearId) {
        return priceRepository.findAllBySchoolYearId(schoolYearId);
    }

    public List<Price> getAllPricesForUser(Long schoolYearId) {
        return priceRepository.findAllExcept(1L, schoolYearId);
    }

    public Price save(Price price, Long schoolYearId) {
        Optional<Price> existingPrice = priceRepository.findPricesByNameAndSchoolYear(price.getName(), schoolYearId);

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
