package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PricesLoader implements CommandLineRunner {
    @Autowired
    private PriceRepository priceRepository;

    @Override
    public void run(String... args) throws Exception {
        if(priceRepository.findPricesByName("Rating Price").isEmpty()){
            priceRepository.save(new Price("Rating Price", 74));
        }
    }
}
