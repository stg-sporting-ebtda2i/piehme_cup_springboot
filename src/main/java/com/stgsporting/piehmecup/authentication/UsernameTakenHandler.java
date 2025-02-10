package com.stgsporting.piehmecup.authentication;

import com.stgsporting.piehmecup.dtos.RegisterDTO;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.exceptions.UsernameTakenException;
import com.stgsporting.piehmecup.services.UserService;

public class UsernameTakenHandler extends RegisterHandler {
    private final UserService userService;

    public UsernameTakenHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(RegisterDTO userRegisterDTO) {
        try {
            userService.getAuthenticatableByUsername(userRegisterDTO.getUsername());
            throw new UsernameTakenException("Username already taken");
        }
        catch (UserNotFoundException e) {
            super.handleRequest(userRegisterDTO);
        }
    }
}