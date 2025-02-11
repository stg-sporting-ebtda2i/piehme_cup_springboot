package com.stgsporting.piehmecup.entities;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import org.springframework.security.core.userdetails.UserDetails;

public interface Details extends UserDetails {
    Long getId();

    Authenticatable getAuthenticatable();
}
