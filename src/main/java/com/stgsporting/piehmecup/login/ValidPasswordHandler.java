package com.stgsporting.piehmecup.login;

import com.stgsporting.piehmecup.dtos.AuthUserInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.InvalidCredentialsException;
import com.stgsporting.piehmecup.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ValidPasswordHandler extends LoginHandler{
    private final User user;
    private final BCryptPasswordEncoder encoder;

    public ValidPasswordHandler(User user) {
        this.user = user;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public AuthUserInfo handle(UserLoginDTO userLoginDTO) {
        if (userLoginDTO.getPassword() == null || userLoginDTO.getPassword().isEmpty()) {
            throw new NullPointerException("Password is required, can't be null");
        }

        if (encoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            AuthUserInfo authUserInfo = new AuthUserInfo();
            authUserInfo.setUserId(this.user.getId());
            authUserInfo.setUsername(this.user.getUsername());

            return authUserInfo;
        } else {
            throw new InvalidCredentialsException("Incorrect email or password");
        }
    }

}
