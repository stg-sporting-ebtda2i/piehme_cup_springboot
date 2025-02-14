package com.stgsporting.piehmecup.services;


import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.LeaderboardDTO;
import com.stgsporting.piehmecup.dtos.UserRegisterDTO;
import com.stgsporting.piehmecup.entities.*;
import com.stgsporting.piehmecup.exceptions.SchoolYearNotFound;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.exceptions.UnauthorizedAccessException;
import com.stgsporting.piehmecup.repositories.IconRepository;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import com.stgsporting.piehmecup.repositories.SchoolYearRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements AuthenticatableService {

    private final UserRepository userRepository;
    private final SchoolYearService schoolYearService;
    private final EntityService entityService;
    private final SchoolYearRepository schoolYearRepository;
    private final PositionRepository positionRepository;
    private final IconRepository iconRepository;

    public UserService(UserRepository userRepository, SchoolYearService schoolYearService
            , EntityService entityService, SchoolYearRepository schoolYearRepository
            , PositionRepository positionRepository, IconRepository iconRepository) {
        this.userRepository = userRepository;
        this.schoolYearService = schoolYearService;
        this.entityService = entityService;
        this.schoolYearRepository = schoolYearRepository;
        this.positionRepository = positionRepository;
        this.iconRepository = iconRepository;
    }

    public User getAuthenticatableById(long id) {
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
        user.setSelectedPosition(positionRepository.findPositionByName("GK").orElseThrow());
        user.setSelectedIcon(iconRepository.findIconByName("Default").orElseThrow());

        user.setQuizId(
                entityService.createEntity(user.getUsername(), user.getSchoolYear())
        );

        save(user);

        return user;
    }

    public List<LeaderboardDTO> getLeaderboard() {

        try {
            Long userId = getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Long schoolYearId = user.getSchoolYear().getId();

            SchoolYear schoolYear = schoolYearRepository.findSchoolYearById(schoolYearId)
                    .orElseThrow(() -> new SchoolYearNotFound("School year not found"));

            List<User> users = userRepository.findUsersBySchoolYear(schoolYear);

            return getLeaderboardDTOS(users);
        } catch (UserNotFoundException | SchoolYearNotFound e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching leaderboard");
        }
    }

    @NotNull
    private static List<LeaderboardDTO> getLeaderboardDTOS(List<User> users) {
        List<LeaderboardDTO> leaderboard = new ArrayList<>();

        for (User u : users){
            LeaderboardDTO dto = new LeaderboardDTO();
            dto.setName(u.getUsername());
            String position = u.getSelectedPosition().getName();
            dto.setPosition(position);
            dto.setLineupRating(u.getLineupRating());
            dto.setUserImgLink(u.getImgLink());
            dto.setIconImgLink(u.getSelectedIcon().getImgLink());
            dto.setCardRating(u.getCardRating());
            leaderboard.add(dto);
        }
        return leaderboard;
    }

    public Integer getCoins() {
        try {
            User user = userRepository.findById(getAuthenticatableId())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            return user.getCoins();
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching coins");
        }
    }
}
