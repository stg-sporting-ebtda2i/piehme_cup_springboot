package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.dtos.users.UserDetailsDTO;
import com.stgsporting.piehmecup.dtos.users.UserInListDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.exceptions.UserNotInSameSchoolYearException;
import com.stgsporting.piehmecup.services.AdminService;
import com.stgsporting.piehmecup.services.AttendanceService;
import com.stgsporting.piehmecup.services.UserService;
import com.stgsporting.piehmecup.services.WalletService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/ostaz/users")
public class UserController {
    private final UserService userService;
    private final AdminService adminService;
    private final WalletService walletService;

    public UserController(UserService userService, AdminService adminService, WalletService walletService) {
        this.userService = userService;
        this.adminService = adminService;
        this.walletService = walletService;
    }

    private User getUser(String userId) {
        User user = userService.getUserByIdOrUsername(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Admin admin = (Admin) adminService.getAuthenticatable();

        if (!Objects.equals(user.getSchoolYear().getId(), admin.getSchoolYear().getId())) {
            throw new UserNotInSameSchoolYearException();
        }

        return user;
    }

    @GetMapping("")
    public ResponseEntity<Object> index(@RequestParam @Nullable Integer page, @RequestParam @Nullable String search) {
        Admin admin = (Admin) adminService.getAuthenticatable();

        Pageable pageable = PageRequest.of(page == null ? 0 : page, 10);

        Page<UserInListDTO> users = userService
                .getUsersBySchoolYear(admin.getSchoolYear(), search, pageable)
                .map(UserInListDTO::new);

        return ResponseEntity.ok(new PaginationDTO<>(users));
    }

    @GetMapping("{userId}")
    public ResponseEntity<Object> show(@PathVariable String userId) {
        User user = getUser(userId);

        return ResponseEntity.ok().body(new UserDetailsDTO(user));
    }

    @PostMapping("{userId}/coins/add")
    public ResponseEntity<Object> addCoins(@PathVariable String userId, @RequestBody JSONObject body) {
        User user = getUser(userId);
        Admin admin = (Admin) adminService.getAuthenticatable();

        Integer coins = (Integer) body.get("coins");

        walletService.credit(user, coins, "By Admin: " + admin.getUsername());

        return ResponseEntity.ok(user.getCoins());
    }

    @PostMapping("{userId}/coins/remove")
    public ResponseEntity<Object> removeCoins(@PathVariable String userId, @RequestBody JSONObject body) {
        User user = getUser(userId);
        Admin admin = (Admin) adminService.getAuthenticatable();

        Integer coins = (Integer) body.get("coins");

        walletService.debit(user, coins, "By Admin: " + admin.getUsername());

        return ResponseEntity.ok(user.getCoins());
    }
}
