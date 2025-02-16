package com.stgsporting.piehmecup.authentication;

import com.stgsporting.piehmecup.dtos.AuthInfo;
import com.stgsporting.piehmecup.dtos.LoginDTO;
import com.stgsporting.piehmecup.exceptions.InvalidCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ValidPasswordHandler extends LoginHandler{
    private final Authenticatable auth;
    private final BCryptPasswordEncoder encoder;

    public ValidPasswordHandler(Authenticatable auth) {
        this.auth = auth;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public AuthInfo handle(LoginDTO loginDTO) {
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            throw new NullPointerException("Password is required, can't be null");
        }

        if (encoder.matches(loginDTO.getPassword(), auth.getPassword())) {
            AuthInfo authUserInfo = new AuthInfo();
            authUserInfo.setUserId(this.auth.getId());
            authUserInfo.setUsername(this.auth.getUsername());
            authUserInfo.setRole(this.auth.getRoleString());

            return authUserInfo;
        } else {
            throw new InvalidCredentialsException();
        }
    }

}
