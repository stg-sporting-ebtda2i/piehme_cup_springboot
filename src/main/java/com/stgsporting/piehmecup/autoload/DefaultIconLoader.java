package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.repositories.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultIconLoader implements CommandLineRunner {

    @Autowired
    private IconRepository iconRepository;

    @Override
    public void run(String... args) throws Exception {
        if(iconRepository.findIconByName("Default").isEmpty()){
            Icon icon = new Icon();
            icon.setAvailable(true);
            icon.setName("Default");
            icon.setPrice(0);
            icon.setImgLink("placeholder.png");
            iconRepository.save(icon);
        }
    }
}
