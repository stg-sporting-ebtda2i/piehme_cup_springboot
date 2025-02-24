package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.users.UserChangeImageDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.exceptions.UserNotInSameSchoolYearException;
import com.stgsporting.piehmecup.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserChangeImageController {

    private static final Logger log = LoggerFactory.getLogger(UserChangeImageController.class);
    private final UserService userService;

    UserChangeImageController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users/change-image")
    public ResponseEntity<Object> changeImageUser(@ModelAttribute UserChangeImageDTO changeImageDTO) {
        User user = (User) userService.getAuthenticatable();

        userService.changeImage(user, changeImageDTO.getImage()).exceptionally(e -> {
            log.error(e.toString());
            return null;
        });

        return ResponseEntity.ok(Map.of("message", "Image changed successfully"));
    }
}
