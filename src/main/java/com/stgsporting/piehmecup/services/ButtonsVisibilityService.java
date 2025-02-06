package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.repositories.ButtonsVisibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ButtonsVisibilityService {
    @Autowired
    private ButtonsVisibilityRepository buttonsVisibilityRepository;

    public List<ButtonsVisibility> findButtonsVisibilityByUserRole(String role) {
        if(role.equalsIgnoreCase("ADMIN")) {
            List<ButtonsVisibility> buttonsVisibility = buttonsVisibilityRepository.findButtonsVisibilityByUserRole(Role.ADMIN);
            buttonsVisibility.addAll(buttonsVisibilityRepository.findButtonsVisibilityByUserRole(Role.OSTAZ));
            return buttonsVisibility;
        }

        else if(role.equalsIgnoreCase("OSTAZ"))
            return buttonsVisibilityRepository.findButtonsVisibilityByUserRole(Role.OSTAZ);

        throw new IllegalArgumentException("Role not found");
    }

    @Transactional
    public void changeVisibilityByName(String name, Boolean visible) {
        buttonsVisibilityRepository.changeVisibilityByName(name, visible);
    }
}
