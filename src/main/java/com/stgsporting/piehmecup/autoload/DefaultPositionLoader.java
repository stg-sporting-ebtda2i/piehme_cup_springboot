package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultPositionLoader implements CommandLineRunner {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (positionRepository.findPositionByName("GK").isEmpty()){
            Position position = new Position();
            position.setName("GK");
            position.setPrice(0);
            positionRepository.save(position);
        }
    }
}
