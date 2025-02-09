package com.stgsporting.piehmecup.entities;

import org.springframework.security.core.userdetails.UserDetails;

public interface Details extends UserDetails {
    Long getId();
}
