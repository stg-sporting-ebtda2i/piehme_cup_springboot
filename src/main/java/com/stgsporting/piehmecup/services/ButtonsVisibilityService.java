package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.ButtonsVisibilityDTO;
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

    public List<ButtonsVisibilityDTO> findButtonsVisibilityByUserRole(Role role) {
        Admin admin = (Admin) adminService.getAuthenticatable();
        SchoolYear schoolYear = admin.getSchoolYear();
        Level level = schoolYear.getLevel();
        if(role == Role.ADMIN)
            return buttonsVisibilityRepository
                    .findAllByLevel(level)
                    .stream().map(ButtonsVisibilityDTO::from).toList();

        if(role == Role.OSTAZ)
            return buttonsVisibilityRepository
                    .findButtonsVisibilityByRoleAndLevel(Role.OSTAZ, level)
                    .stream().map(ButtonsVisibilityDTO::from).toList();

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
        SchoolYear schoolYear = admin.getSchoolYear();
        Level level = schoolYear.getLevel();
        ButtonsVisibility visibility = buttonsVisibilityRepository.findByNameAndLevel(name, level)
                .orElseThrow(() -> new NotFoundException("Button not found"));

        if(admin.getRole() != Role.ADMIN && admin.getRole() != visibility.getRole()) {
            throw new NotFoundException("You are not allowed to change this button visibility");
        }

        visibility.setVisible(visible);
        buttonsVisibilityRepository.save(visibility);
    }
}
