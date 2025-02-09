package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.AuthInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.dtos.UserRegisterDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.UserAlreadyExistException;
import com.stgsporting.piehmecup.authentication.LoginHandler;
import com.stgsporting.piehmecup.authentication.CheckIfUserExistsHandler;
import com.stgsporting.piehmecup.authentication.RegisterHandler;
import com.stgsporting.piehmecup.authentication.UsernameTakenHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AuthInfo register(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null || userRegisterDTO.getUsername() == null || userRegisterDTO.getPassword() == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }

        RegisterHandler handlerChain = new UsernameTakenHandler(userService);

        handlerChain.handleRequest(userRegisterDTO);

        try {
            User user = createUserFromDTO(userRegisterDTO);
            userService.save(user);

            return getAuthInfo(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Username already in use");
        }
    }

    private AuthInfo getAuthInfo(User user){
        AuthInfo authUserInfo = new AuthInfo();
        authUserInfo.setUserId(user.getId());
        authUserInfo.setUsername(user.getUsername());
        authUserInfo.setJWTToken(jwtService.generateUserToken(authUserInfo));

        return authUserInfo;
    }

    private User createUserFromDTO(UserRegisterDTO userRegisterDTO){
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(encoder.encode(userRegisterDTO.getPassword()));
        user.setSchoolYear(schoolYearService.getShoolYearByName(userRegisterDTO.getSchoolYear()));
        user.setCoins(0);
        user.setCardRating(0);
        user.setLineupRating(0.0);
        user.setImgLink(userRegisterDTO.getImgLink());
        return user;
    }

    public AuthInfo login(UserLoginDTO userLoginDTO) {
        LoginHandler loginHandler = new CheckIfUserExistsHandler(userService);
        AuthInfo authInfo = loginHandler.handle(userLoginDTO);
        authInfo.setJWTToken(jwtService.generateUserToken(authInfo));
        return authInfo;
    }
}
