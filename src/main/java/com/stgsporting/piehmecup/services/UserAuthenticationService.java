package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.AuthInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.authentication.LoginHandler;
import com.stgsporting.piehmecup.authentication.CheckIfUserExistsHandler;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationService {
    private final UserService userService;
    private final SchoolYearService schoolYearService;
    private final JWTService jwtService;

    UserAuthenticationService(UserService userService, SchoolYearService schoolYearService, JWTService jwtService){
        this.userService = userService;
        this.schoolYearService = schoolYearService;
        this.jwtService = jwtService;
    }


    public AuthInfo login(UserLoginDTO userLoginDTO) {
        LoginHandler loginHandler = new CheckIfUserExistsHandler(userService);
        AuthInfo authInfo = loginHandler.handle(userLoginDTO);
        authInfo.setJWTToken(jwtService.generateUserToken(authInfo));
        return authInfo;
    }
}
