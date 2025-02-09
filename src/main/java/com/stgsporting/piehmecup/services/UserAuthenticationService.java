package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.AuthUserInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.dtos.UserSignupDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.UserAlreadyExistException;
import com.stgsporting.piehmecup.login.LoginHandler;
import com.stgsporting.piehmecup.login.UserExistsHandler;
import com.stgsporting.piehmecup.signup.SignupHandler;
import com.stgsporting.piehmecup.signup.UserAlreadyExistHandler;
import com.stgsporting.piehmecup.signup.UsernameTakenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserAuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolYearService schoolYearService;
    @Autowired
    private JWTService jwtService;

    @Transactional
    public AuthUserInfo register(UserSignupDTO userSignupDTO) {
        if (userSignupDTO == null || userSignupDTO.getUsername() == null || userSignupDTO.getPassword() == null) {
            throw new IllegalArgumentException("Username, Password must not be null");
        }

        SignupHandler handlerChain = new UserAlreadyExistHandler(userService)
                .setNextHandler(new UsernameTakenHandler(userService));

        handlerChain.handleRequest(userSignupDTO);

        try {
            User user = createUserFromDTO(userSignupDTO);
            userService.saveUser(user);

            AuthUserInfo authUserInfo = new AuthUserInfo();
            authUserInfo.setUserId(user.getId());
            authUserInfo.setUsername(user.getUsername());

            authUserInfo.setJWTToken(jwtService.generateToken(authUserInfo));

            return authUserInfo;
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Username already in use");
        }
    }

    private User createUserFromDTO(UserSignupDTO userSignupDTO){
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setUsername(userSignupDTO.getUsername());
        user.setPassword(encoder.encode(userSignupDTO.getPassword()));
        user.setSchoolYear(schoolYearService.getShoolYearByName(userSignupDTO.getSchoolYear()));
        user.setCoins(0);
        user.setCardRating(0);
        user.setLineupRating(0.0);
        user.setImgLink(userSignupDTO.getImgLink());
        return user;
    }

    public AuthUserInfo login(UserLoginDTO userLoginDTO) {
        LoginHandler loginHandler = new UserExistsHandler(userService);
        AuthUserInfo authUserInfo = loginHandler.handle(userLoginDTO);
        authUserInfo.setJWTToken(jwtService.generateToken(authUserInfo));
        return authUserInfo;
    }
}
