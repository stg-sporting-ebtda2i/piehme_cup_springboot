package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public void saveAdmin() {
        Admin admin = new Admin();
//        admin.setUser(user);
        adminRepository.save(admin);
    }
}
