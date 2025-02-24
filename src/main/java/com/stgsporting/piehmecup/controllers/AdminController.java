package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.dtos.admins.AdminFormDTO;
import com.stgsporting.piehmecup.dtos.admins.AdminDetailsDTO;
import com.stgsporting.piehmecup.dtos.admins.AdminInListDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.exceptions.UnauthorizedAccessException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.services.AdminService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("")
    public ResponseEntity<Object> index(final Pageable pageable) {
        PageRequest newPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(
                new PaginationDTO<>(adminService.getAllAdmins(newPage).map(AdminInListDTO::new))
        );
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Object> show(@PathVariable String adminId) {
        Admin admin = adminService.getAdminByIdOrUsername(adminId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found"));

        return ResponseEntity.ok(new AdminDetailsDTO(admin));
    }

    @PostMapping("")
    public ResponseEntity<Object> store(@RequestBody AdminFormDTO adminDTO) {
        Admin admin = adminService.createAdmin(adminDTO);

        return ResponseEntity.ok(new AdminDetailsDTO(admin));
    }

    @PatchMapping("/{adminId}")
    public ResponseEntity<Object> update(@PathVariable String adminId, @RequestBody AdminFormDTO adminDTO) {
        Admin admin = adminService.getAdminByIdOrUsername(adminId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found"));

        Admin currentAdmin = (Admin) adminService.getAuthenticatable();
        if(currentAdmin.equals(admin))
            throw new UnauthorizedAccessException("You can not update your own account");

        adminService.updateAdmin(admin, adminDTO);

        return ResponseEntity.ok(new AdminDetailsDTO(admin));
    }
}
