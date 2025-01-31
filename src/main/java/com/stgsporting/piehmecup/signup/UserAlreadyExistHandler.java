package com.stgsporting.piehmecup.signup;

import com.stgsporting.piehmecup.dtos.UserSignupDTO;
import com.stgsporting.piehmecup.exceptions.UserAlreadyExistException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.services.UserService;

public class UserAlreadyExistHandler extends SignupHandler {
    private final UserService userService;

    public UserAlreadyExistHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleRequest(UserSignupDTO userSignupDTO) {
        try {
            userService.getUserByUsername(userSignupDTO.getUsername());
            throw new UserAlreadyExistException("Email already in use");
        } catch (UserNotFoundException e) {
            if (nextHandler != null) {
                nextHandler.handleRequest(userSignupDTO);
            }
        }
    }

}