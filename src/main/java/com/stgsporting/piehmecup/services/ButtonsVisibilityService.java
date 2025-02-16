package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.exceptions.NotFoundException;
import com.stgsporting.piehmecup.repositories.ButtonsVisibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ButtonsVisibilityService {
    @Autowired
    private ButtonsVisibilityRepository buttonsVisibilityRepository;
    @Autowired
    private AdminService adminService;

    public List<ButtonsVisibility> findButtonsVisibilityByUserRole(Role role) {
        if(role == Role.ADMIN)
            return buttonsVisibilityRepository.findAll();

        if(role == Role.OSTAZ)
            return buttonsVisibilityRepository.findButtonsVisibilityByRole(Role.OSTAZ);

        throw new NotFoundException("Role not found");
    }

    @Transactional
    public void changeVisibilityByName(String name, Boolean visible) {
        Admin admin = (Admin) adminService.getAuthenticatable();

        ButtonsVisibility visibility = buttonsVisibilityRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Button not found"));

        if(admin.getRole() != Role.ADMIN && admin.getRole() != visibility.getRole()) {
            throw new NotFoundException("You are not allowed to change this button visibility");
        }

        visibility.setVisible(visible);
        buttonsVisibilityRepository.save(visibility);
    }
}
