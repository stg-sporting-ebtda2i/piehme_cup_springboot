package com.stgsporting.piehmecup.services;


import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.UserRegisterDTO;
import com.stgsporting.piehmecup.entities.*;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.exceptions.UnauthorizedAccessException;
import com.stgsporting.piehmecup.repositories.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AuthenticatableService {

    private final UserRepository userRepository;
    private final SchoolYearService schoolYearService;
    private final EntityService entityService;

    public UserService(UserRepository userRepository, SchoolYearService schoolYearService, EntityService entityService) {
        this.userRepository = userRepository;
        this.schoolYearService = schoolYearService;
        this.entityService = entityService;
    }

    public User getAuthenticatableById(long id){
        return userRepository.findUserById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    public long getAuthenticatableId() {
        return getAuthenticatable().getId();
    }

    public Authenticatable getAuthenticatable() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /*
         * If the user is not authenticated or the principal is not an instance of UserDetail, throw an UnauthorizedAccessException
         * This case should not happen, because it means caller expects authenticated user to be present
         * We would not have reached this point if the user was not authenticated
         * but for security reasons, we should check this case
         */
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetail))
            throw new UnauthorizedAccessException("User is not authenticated");

        return  ((Details) authentication.getPrincipal()).getAuthenticatable();
    }

    public User getAuthenticatableByUsername(String username){
        if (username == null || username.isEmpty())
            throw new NullPointerException("Username cannot be empty");

        return userRepository.findUsersByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public void save(Authenticatable user) {
        userRepository.save((User) user);
    }

    public User createUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setSchoolYear(schoolYearService.getShoolYearByName(userRegisterDTO.getSchoolYear()));
        user.setCoins(0);
        user.setCardRating(0);
        user.setLineupRating(0.0);
        user.setImgLink(userRegisterDTO.getImgLink());

        user.setQuizId(
                entityService.createEntity(user.getUsername(), user.getSchoolYear())
        );

        save(user);

        return user;
    }
}
