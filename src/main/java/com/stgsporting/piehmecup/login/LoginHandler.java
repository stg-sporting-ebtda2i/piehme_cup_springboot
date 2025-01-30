package com.stgsporting.piehmecup.login;

import com.stgsporting.piehmecup.dtos.AuthUserInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;

public abstract class LoginHandler {
    protected LoginHandler next;

    public LoginHandler setNextHandler(LoginHandler handler){
        next = handler;
        return handler;
    }
    public abstract AuthUserInfo handle(UserLoginDTO userLoginDTO);
}
