package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PricesLoader implements CommandLineRunner {
    @Autowired
    private PriceRepository priceRepository;

    @Override
    public void run(String... args) throws Exception {
        Map<String, Integer> prices = new HashMap<>(Map.of(
                "Rating Price", 1100,
                "Madares el Ahad", 1140,
                "Odas" , 1900,
                "Odas El-Gom3a", 1900,
                "Pisagi", 1140,
                "Tasbeha", 1520
        ));

        prices.forEach((name, price) -> {
            if(priceRepository.findPricesByName(name).isEmpty()) {
                priceRepository.save(new Price(name, price));
            }
        });
    }
}
