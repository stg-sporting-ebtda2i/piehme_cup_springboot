package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.AuthUserInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.login.LoginHandler;
import com.stgsporting.piehmecup.login.UserExistsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;
    public AuthUserInfo verify(UserLoginDTO userLoginDTO) {
        LoginHandler loginHandler = new UserExistsHandler(userService);
        AuthUserInfo authUserInfo = loginHandler.handle(userLoginDTO);
        authUserInfo.setJWTToken(jwtService.generateToken(authUserInfo));
        return authUserInfo;
    }
}
