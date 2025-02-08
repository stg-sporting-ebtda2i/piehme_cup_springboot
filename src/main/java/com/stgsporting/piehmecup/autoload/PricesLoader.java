package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PricesLoader implements CommandLineRunner {
    @Autowired
    private PricesRepository pricesRepository;

    @Override
    public void run(String... args) throws Exception {
        if(pricesRepository.findPricesByName("Rating Price").isEmpty()){
            pricesRepository.save(new Price("Rating Price", 74));
        }
    }
}
