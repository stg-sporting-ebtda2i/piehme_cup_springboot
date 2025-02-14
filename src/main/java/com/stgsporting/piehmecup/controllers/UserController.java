package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.users.UserDetailsDTO;
import com.stgsporting.piehmecup.dtos.users.UserInListDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.services.AdminService;
import com.stgsporting.piehmecup.services.AttendanceService;
import com.stgsporting.piehmecup.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/ostaz/users")
public class UserController {
    private final UserService userService;
    private final AdminService adminService;

    public UserController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("")
    public ResponseEntity<Object> index(@RequestParam @Nullable Integer page, @RequestParam @Nullable String search) {
        Admin admin = (Admin) adminService.getAuthenticatable();

        Pageable pageable = PageRequest.of(page == null ? 0 : page, 10);

        return ResponseEntity.ok(
                userService.getUsersBySchoolYear(admin.getSchoolYear(), search, pageable).stream().map(UserInListDTO::new)
        );
    }

    @GetMapping("{userId}")
    public ResponseEntity<Object> show(@PathVariable Long userId) {
        User user = userService.getAuthenticatableById(userId);
        Admin admin = (Admin) adminService.getAuthenticatable();
        if (!Objects.equals(user.getSchoolYear().getId(), admin.getSchoolYear().getId())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("User not in your school year");
        }

        return ResponseEntity.ok().body(new UserDetailsDTO(user));
    }
}
