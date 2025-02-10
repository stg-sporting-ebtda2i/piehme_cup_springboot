package com.stgsporting.piehmecup.authentication;

import com.stgsporting.piehmecup.dtos.LoginDTO;
import com.stgsporting.piehmecup.dtos.AuthInfo;
import com.stgsporting.piehmecup.services.AuthenticatableService;

public class CheckIfUserExistsHandler extends LoginHandler {
    private final AuthenticatableService authService;

    public CheckIfUserExistsHandler(AuthenticatableService authService) {
        this.authService = authService;
    }

    @Override
    public AuthInfo handle(LoginDTO loginDTO) {
        if (loginDTO.getUsername() == null){
            throw new NullPointerException("Username is required, can't be null");
        }

        if (authService.getAuthenticatableByUsername(loginDTO.getUsername()) == null){
            throw new NullPointerException("User does not exist");
        }
        Authenticatable auth;

        try {
            auth = authService.getAuthenticatableByUsername(loginDTO.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this.setNextHandler(new ValidPasswordHandler(auth)).handle(loginDTO);
    }

}
