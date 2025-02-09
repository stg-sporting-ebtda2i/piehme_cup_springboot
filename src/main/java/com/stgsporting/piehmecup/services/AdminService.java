package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements AuthenticatableService {
    @Autowired
    private AdminRepository adminRepository;


    @Override
    public Authenticatable getAuthenticatableById(long id) {
        return adminRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    @Override
    public long getAuthenticatableId() {
        return 0;
    }

    @Override
    public Authenticatable getAuthenticatableByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    @Override
    public void save(Authenticatable authenticatable) {
        adminRepository.save((Admin) authenticatable);
    }
}
