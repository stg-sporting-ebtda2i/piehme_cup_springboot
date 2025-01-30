package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Ostaz;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.repositories.OstazRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OstazService {
    @Autowired
    private OstazRepository ostazRepository;

    public void saveOstaz(User user) {
        Ostaz ostaz = new Ostaz();
        ostaz.setUser(user);
        ostazRepository.save(ostaz);
    }
}
