package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Position;
import com.stgsporting.piehmecup.enums.Positions;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PositionLoader implements CommandLineRunner {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] positions = Arrays.stream(Positions.values()).map(Enum::name).toArray(String[]::new);

        for (String positionName : positions) {
            if (positionRepository.findPositionByName(positionName).isEmpty()) {
                Position position = new Position();
                position.setName(positionName);
                position.setPrice(100);
                positionRepository.save(position);
            }
        }
    }
}
