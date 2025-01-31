package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.entities.UserDetail;
import com.stgsporting.piehmecup.exceptions.UnauthorizedAccessException;
import com.stgsporting.piehmecup.repositories.AdminRepository;
import com.stgsporting.piehmecup.repositories.OstazRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import com.stgsporting.piehmecup.repositories.WaladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WaladRepository waladRepository;
    @Autowired
    private OstazRepository ostazRepository;
    @Autowired
    private AdminRepository adminRepository;

    public User getUserById(long id){
        return userRepository.findUserById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    public long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /*
         * If the user is not authenticated or the principal is not an instance of UserDetail, throw an UnauthorizedAccessException
         * This case should not happen, because it means caller expects authenticated user to be present
         * We would not have reached this point if the user was not authenticated
         * but for security reasons, we should check this case
         */
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetail))
            throw new UnauthorizedAccessException("User is not authenticated or invalid principal");

        return  ((UserDetail)authentication.getPrincipal()).getUserId();
    }

    public User getUserByUsername(String username){
        if (username == null || username.isEmpty())
            throw new NullPointerException("Username cannot be empty");

        return userRepository.findUsersByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("Incorrect email or password"));
    }

    public Enum<Role> getRole(User user){
        if(waladRepository.findByUser(user).isPresent())
            return Role.WALAD;

        else if (ostazRepository.findByUser(user).isPresent())
            return Role.OSTAZ;

        else if (adminRepository.findByUser(user).isPresent())
            return Role.OSTAZ;

        else
            throw new UserNotFoundException("User not found");

    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
