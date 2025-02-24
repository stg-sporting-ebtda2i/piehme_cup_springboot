package com.stgsporting.piehmecup.services;


import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.users.LeaderboardDTO;
import com.stgsporting.piehmecup.dtos.UserRegisterDTO;
import com.stgsporting.piehmecup.dtos.users.UserInLeaderboardDTO;
import com.stgsporting.piehmecup.entities.*;
import com.stgsporting.piehmecup.exceptions.*;
import com.stgsporting.piehmecup.repositories.IconRepository;
import com.stgsporting.piehmecup.repositories.PositionRepository;
import com.stgsporting.piehmecup.repositories.SchoolYearRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService implements AuthenticatableService {

    private final UserRepository userRepository;
    private final SchoolYearService schoolYearService;
    private final EntityService entityService;
    private final SchoolYearRepository schoolYearRepository;
    private final PositionRepository positionRepository;
    private final IconRepository iconRepository;
    private final FileService fileService;
    private final RemoveBackgroundService removeBackgroundService;

    public UserService(UserRepository userRepository, SchoolYearService schoolYearService
            , EntityService entityService, SchoolYearRepository schoolYearRepository
            , PositionRepository positionRepository, IconRepository iconRepository, FileService fileService
            , RemoveBackgroundService removeBackgroundService) {
        this.userRepository = userRepository;
        this.schoolYearService = schoolYearService;
        this.entityService = entityService;
        this.schoolYearRepository = schoolYearRepository;
        this.positionRepository = positionRepository;
        this.iconRepository = iconRepository;
        this.fileService = fileService;
        this.removeBackgroundService = removeBackgroundService;
    }

    public User getAuthenticatableById(long id) {
        return getUserById(id).orElseThrow(UserNotFoundException::new);
    }

    public long getAuthenticatableId() {
        return getAuthenticatable().getId();
    }

    public Authenticatable getAuthenticatable() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetail))
            throw new UnauthorizedAccessException();

        return  ((Details) authentication.getPrincipal()).getAuthenticatable();
    }

    public User getAuthenticatableByUsername(String username){
        if (username == null || username.isEmpty())
            throw new NullPointerException("Username cannot be empty");

        return getUserByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);
    }

    public Optional<User> getUserByIdOrUsername(String idOrUsername) {
        if (idOrUsername.matches("\\d+")) {
            Optional<User> user = getUserById(Long.parseLong(idOrUsername));

            if (user.isPresent())
                return user;
        }

        return getUserByUsername(idOrUsername);
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    public void save(Authenticatable user) {
        userRepository.save((User) user);
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> changeImage(User user, MultipartFile image) {
        try {
            image = removeBackgroundService.handle(image);

            String key = fileService.uploadFile(image, "/users");

            if (user.getImgLink() != null && !user.getImgLink().isEmpty()) {
                fileService.deleteFile(user.getImgLink());
            }

            user.setImgLink(key);

            save(user);
            return CompletableFuture.completedFuture(null);
        }catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Transactional
    public User createUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();

        validateUsername(userRegisterDTO.getUsername());
        user.setUsername(userRegisterDTO.getUsername());

        validatePassword(userRegisterDTO.getPassword());
        user.setPassword(userRegisterDTO.getPassword());

        user.setSchoolYear(schoolYearService.getShoolYearByName(userRegisterDTO.getSchoolYear()));
        user.setCoins(0);
        user.setCardRating(50);
        user.setImgLink(userRegisterDTO.getImgLink());
        user.setSelectedPosition(positionRepository.findPositionByName("GK").orElseThrow());
        user.setSelectedIcon(iconRepository.findIconByName("Default").orElseThrow());

        user.setQuizId(
                entityService.createEntity(user.getUsername(), user.getSchoolYear())
        );

        Icon icon = iconRepository.findIconByName("Default").orElseThrow();
        Position position = positionRepository.findPositionByName("GK").orElseThrow();
        List<Icon> icons = new ArrayList<>();
        List<Position> positions = new ArrayList<>();
        icons.add(icon);
        positions.add(position);
        user.setIcons(icons);
        user.setPositions(positions);

        save(user);

        return user;
    }

    public Page<User> getUsersBySchoolYear(SchoolYear schoolYear, String search, Pageable page) {
        if(search == null) search = "";

        return userRepository.findUsersBySchoolYearPaginated(schoolYear,search + "%", page);
    }

    public LeaderboardDTO getLeaderboard() {
        Long userId = getAuthenticatableId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Long schoolYearId = user.getSchoolYear().getId();
        SchoolYear schoolYear = schoolYearRepository.findSchoolYearById(schoolYearId).orElseThrow(SchoolYearNotFound::new);

        List<User> users = userRepository.findUsersBySchoolYear(schoolYear);
//        List<User> users = userRepository.findUsersBySchoolYear(schoolYear);

        return getLeaderboardDTO(users);
    }

    @NotNull
    private LeaderboardDTO getLeaderboardDTO(List<User> users) {
        List<UserInLeaderboardDTO> usersInLeaderboard = new ArrayList<>();

        Double maxRating = 0.0;
        Double avgRating = 0.0;

        for (User u : users) {
            UserInLeaderboardDTO dto = new UserInLeaderboardDTO();
            dto.setName(u.getUsername());
            dto.setId(u.getId());
            String position = u.getSelectedPosition().getName();
            dto.setPosition(position);
            Double rating = u.getLineupRating();

            dto.setLineupRating(rating);

            if (rating > maxRating) {
                maxRating = rating;
            }
            avgRating += rating;

            dto.setImageKey(u.getImgLink());
            dto.setImageUrl(fileService.generateSignedUrl(u.getImgLink()));

            dto.setIconKey(u.getSelectedIcon().getImgLink());
            dto.setIconUrl(fileService.generateSignedUrl(u.getSelectedIcon().getImgLink()));

            dto.setCardRating(u.getCardRating());
            usersInLeaderboard.add(dto);
        }

        LeaderboardDTO leaderboard = new LeaderboardDTO();
        leaderboard.setUsers(usersInLeaderboard);
        leaderboard.setMaxRating(Math.round(maxRating * 100.0) / 100.0);
        leaderboard.setAvgRating(users.isEmpty() ? 0.0 : avgRating / users.size());
        leaderboard.setAvgRating(Math.round(leaderboard.getAvgRating() * 100.0) / 100.0);

        return leaderboard;
    }

    public Integer getCoins() {
        User user = userRepository.findById(getAuthenticatableId()).orElseThrow(UserNotFoundException::new);
        return user.getCoins();
    }

    public void changePassword(User user, String password) {
        validatePassword(password);

        user.setPassword(password);
        save(user);
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty())
            throw new ChangePasswordException("Password cannot be empty");

        if (password.length() < 4 || password.length() > 64)
            throw new ChangePasswordException("Password must be between 6 and 64 characters");
    }

    private void validateUsername(String username) {
        if (username == null || username.isEmpty())
            throw new UsernameTakenException("Username cannot be empty");

        if(userRepository.existsByUsername(username)) {
            throw new UsernameTakenException("Username already exists");
        }
    }
}
