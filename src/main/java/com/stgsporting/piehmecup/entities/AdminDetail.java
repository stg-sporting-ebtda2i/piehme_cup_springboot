package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
public class AdminDetail implements Details {
    private Admin admin;

    public AdminDetail(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(admin.getRole().name()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    public Long getId() {
        return admin.getId();
    }

    public Authenticatable getAuthenticatable() {
        return admin;
    }
}
