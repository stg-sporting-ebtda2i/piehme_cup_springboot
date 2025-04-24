package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.entities.*;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.exceptions.NotFoundException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.ButtonsVisibilityRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
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
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public List<ButtonsVisibility> findButtonsVisibilityByUserRole(Role role) {
        if(role == Role.ADMIN)
            return buttonsVisibilityRepository.findAll();

        if(role == Role.OSTAZ)
            return buttonsVisibilityRepository.findButtonsVisibilityByRole(Role.OSTAZ);

        throw new NotFoundException("Role not found");
    }

    public List<ButtonsVisibility> getAllButtonsVisibility() {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        SchoolYear schoolYear = user.getSchoolYear();
        Level level = schoolYear.getLevel();
        return buttonsVisibilityRepository.findAllVisible(level);
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
