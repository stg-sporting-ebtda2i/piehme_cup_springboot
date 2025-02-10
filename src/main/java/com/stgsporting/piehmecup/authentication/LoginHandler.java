package com.stgsporting.piehmecup.authentication;

import com.stgsporting.piehmecup.dtos.LoginDTO;
import com.stgsporting.piehmecup.dtos.AuthInfo;

public abstract class LoginHandler {
    protected LoginHandler next;

    public LoginHandler setNextHandler(LoginHandler handler){
        next = handler;
        return handler;
    }
    public abstract AuthInfo handle(LoginDTO userLoginDTO);
}
