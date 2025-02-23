package com.stgsporting.piehmecup.autoload;

import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.entities.Level;
import com.stgsporting.piehmecup.repositories.IconRepository;
import com.stgsporting.piehmecup.services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultIconLoader implements CommandLineRunner {

    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private LevelService levelService;

    @Override
    public void run(String... args) throws Exception {
        Level ebteda2i = levelService.getLevelById(1L);
        Level e3dady = levelService.getLevelById(2L);

        if(iconRepository.findIconByName("Default").isEmpty()){
            Icon icon = new Icon();
            icon.setAvailable(true);
            icon.setName("Default");
            icon.setLevel(ebteda2i);
            icon.setPrice(0);
            icon.setImgLink("placeholder.png");
            iconRepository.save(icon);
        }

        if(iconRepository.findIconByName("DefaultIcon").isEmpty()){
            Icon icon = new Icon();
            icon.setAvailable(true);
            icon.setName("DefaultIcon");
            icon.setLevel(e3dady);
            icon.setPrice(0);
            icon.setImgLink("placeholderIcon.png");
            iconRepository.save(icon);
        }
    }
}
