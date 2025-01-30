package com.stgsporting.piehmecup.signup;

import com.stgsporting.piehmecup.dtos.UserSignupDTO;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.exceptions.UsernameTakenException;
import com.stgsporting.piehmecup.services.UserService;

public class UsernameTakenHandler extends SignupHandler {
    private final UserService userService;

    public UsernameTakenHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        try {
            userService.getUserByUsername(userSignupDTO.getUsername());
            throw new UsernameTakenException("Username already taken");
        }
        catch (UserNotFoundException e) {
            super.handleRequest(userSignupDTO);
        }
    }
}