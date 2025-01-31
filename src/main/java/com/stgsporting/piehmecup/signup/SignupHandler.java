package com.stgsporting.piehmecup.signup;

import com.stgsporting.piehmecup.dtos.UserSignupDTO;

public abstract class SignupHandler {
    protected SignupHandler nextHandler;

    public SignupHandler setNextHandler(SignupHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

    public void handleRequest(UserSignupDTO userSignupDTO) {
        if (nextHandler != null) {
            nextHandler.handleRequest(userSignupDTO);
        }
    }
}
