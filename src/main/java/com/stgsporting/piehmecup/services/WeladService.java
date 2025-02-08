package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.entities.Walad;
import com.stgsporting.piehmecup.repositories.WaladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeladService {
    @Autowired
    private WaladRepository waladRepository;

    public void saveWelad(User user, String imgLink) {
        Walad walad = new Walad();
        walad.setUser(user);
        walad.setImgLink(imgLink);
        walad.setCoins(0);
        walad.setCardRating(0);
        walad.setLineupRating(0.0);
        waladRepository.save(walad);
    }
}
