package com.stgsporting.piehmecup.login;

import com.stgsporting.piehmecup.dtos.AuthUserInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.services.UserService;

public class RoleCheckHandler extends LoginHandler{
    private final User user;
    private final UserService userService;

    public RoleCheckHandler(User user, UserService userService) {
        this.user = user;
        this.userService = userService;
    }

    @Override
    public AuthUserInfo handle(UserLoginDTO userLoginDTO) {
        Enum<Role> role = userService.getRole(this.user);
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setRole(role);
        authUserInfo.setUserId(this.user.getId());
        authUserInfo.setUsername(this.user.getUsername());
        return authUserInfo;
    }

}
