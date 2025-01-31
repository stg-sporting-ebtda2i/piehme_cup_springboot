package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.UserSignupDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.UserAlreadyExistException;
import com.stgsporting.piehmecup.signup.SignupHandler;
import com.stgsporting.piehmecup.signup.UserAlreadyExistHandler;
import com.stgsporting.piehmecup.signup.UsernameTakenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SignupService {
    @Autowired
    private UserService userService;
    @Autowired
    private OsraService osraService;
    @Autowired
    private WeladService weladService;
    @Autowired
    private OstazService ostazService;
    @Autowired
    private AdminService adminService;

    @Transactional
    public void signup(UserSignupDTO userSignupDTO) {
        if (userSignupDTO == null || userSignupDTO.getUsername() == null || userSignupDTO.getPassword() == null) {
            throw new IllegalArgumentException("Username, Password must not be null");
        }

        SignupHandler handlerChain = new UserAlreadyExistHandler(userService)
                .setNextHandler(new UsernameTakenHandler(userService));

        handlerChain.handleRequest(userSignupDTO);

        try {
            User user = createUserFromDTO(userSignupDTO);
            userService.saveUser(user);
            System.out.println(userSignupDTO);
            saveInRoleTables(userSignupDTO, user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Username already in use");
        }
    }

    private void saveInRoleTables(UserSignupDTO userSignupDTO, User user) {
        if (userSignupDTO.getRole() == null) {
            throw new IllegalArgumentException("Role must not be null");
        }

        switch (userSignupDTO.getRole() ) {
            case "WELAD" -> weladService.saveWelad(user, userSignupDTO.getImgLink());
            case "OSTAZ" -> ostazService.saveOstaz(user);
            case "ADMIN" -> adminService.saveAdmin(user);
            default -> throw new IllegalArgumentException("Invalid role: " + userSignupDTO.getRole());
        }
    }

    private User createUserFromDTO(UserSignupDTO userSignupDTO){
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setUsername(userSignupDTO.getUsername());
        user.setPassword(encoder.encode(userSignupDTO.getPassword()));
        user.setOsra(osraService.getOsraByName(userSignupDTO.getOsra()));
        return user;
    }
}
