package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.admins.AdminFormDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.AdminDetail;
import com.stgsporting.piehmecup.entities.Details;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.enums.Role;
import com.stgsporting.piehmecup.exceptions.InvalidCredentialsException;
import com.stgsporting.piehmecup.exceptions.NotFoundException;
import com.stgsporting.piehmecup.exceptions.UnauthorizedAccessException;
import com.stgsporting.piehmecup.exceptions.UsernameTakenException;
import com.stgsporting.piehmecup.repositories.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminService implements AuthenticatableService {

    private final AdminRepository adminRepository;
    private final SchoolYearService schoolYearService;

    AdminService(AdminRepository adminRepository, SchoolYearService schoolYearService) {
        this.adminRepository = adminRepository;
        this.schoolYearService = schoolYearService;
    }

    public Optional<Admin> getAdminById(long id) {
        return adminRepository.findById(id);
    }

    public Optional<Admin> getAdminByIdOrUsername(String idOrUsername) {
        if (idOrUsername.matches("\\d+")) {
            Optional<Admin> admin = getAdminById(Long.parseLong(idOrUsername));

            if (admin.isPresent())
                return admin;
        }

        return getAdminByUsername(idOrUsername);
    }

    public Page<Admin> getAllAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Authenticatable getAuthenticatableById(long id) {
        return getAdminById(id).orElseThrow(InvalidCredentialsException::new);
    }

    @Override
    public long getAuthenticatableId() {
        return getAuthenticatable().getId();
    }

    @Override
    public Authenticatable getAuthenticatable() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AdminDetail))
            throw new UnauthorizedAccessException("Admin is not authenticated");

        return ((Details) authentication.getPrincipal()).getAuthenticatable();
    }

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Authenticatable getAuthenticatableByUsername(String username) {
        return getAdminByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);
    }

    @Override
    public void save(Authenticatable authenticatable) {
        adminRepository.save((Admin) authenticatable);
    }

    public Admin createAdmin(AdminFormDTO adminDTO) {
        Admin admin = new Admin();
        this.getAdminByUsername(adminDTO.getUsername())
                .ifPresent(a -> {
                    throw new UsernameTakenException("Username already exists");
                });

        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(adminDTO.getPassword());
        Role role = Role.lookup(adminDTO.getRole()).orElseThrow(() -> new NotFoundException("Invalid role"));
        admin.setRole(role);

        SchoolYear schoolYear = schoolYearService.getShoolYearById(adminDTO.getSchoolYear());
        admin.setSchoolYear(schoolYear);

        return adminRepository.save(admin);
    }

    public void updateAdmin(Admin admin, AdminFormDTO adminDTO) {
        if (adminDTO.getUsername() != null && !adminDTO.getUsername().isEmpty() && !adminDTO.getUsername().isBlank()) {
            Optional<Admin> oldAdmin = this.getAdminByUsername(adminDTO.getUsername());
            if (oldAdmin.isPresent() && !Objects.equals(oldAdmin.get().getId(), admin.getId())) {
                throw new UsernameTakenException("Username already exists");
            }

            admin.setUsername(adminDTO.getUsername());
        }
        if (adminDTO.getPassword() != null && !adminDTO.getPassword().isEmpty() && !adminDTO.getPassword().isBlank()) {
            admin.setPassword(adminDTO.getPassword());
        }

        if (adminDTO.getRole() != null && !adminDTO.getRole().isEmpty() && !adminDTO.getRole().isBlank()) {
            Role role = Role.lookup(adminDTO.getRole()).orElseThrow(() -> new NotFoundException("Invalid role"));
            admin.setRole(role);
        }

        if (adminDTO.getSchoolYear() != null && adminDTO.getSchoolYear() != 0) {
            SchoolYear schoolYear = schoolYearService.getShoolYearById(adminDTO.getSchoolYear());
            admin.setSchoolYear(schoolYear);
        }

        adminRepository.save(admin);
    }
}
