package com.stgsporting.piehmecup.login;

import com.stgsporting.piehmecup.dtos.AuthUserInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.services.UserService;

public class UserExistsHandler extends LoginHandler{
    private final UserService userService;

    public UserExistsHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthUserInfo handle(UserLoginDTO userLoginDTO) {
        if (userLoginDTO.getUsername() == null){
            throw new NullPointerException("Username is required, can't be null");
        }

        if (userService.getUserByUsername(userLoginDTO.getUsername()) == null){
            throw new NullPointerException("User does not exist");
        }
        User user;

        try {
            user = userService.getUserByUsername(userLoginDTO.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this.setNextHandler(new ValidPasswordHandler(user)).handle(userLoginDTO);
    }

}
