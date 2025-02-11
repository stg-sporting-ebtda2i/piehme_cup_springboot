package com.stgsporting.piehmecup.services;


import com.stgsporting.piehmecup.authentication.Authenticatable;

public interface AuthenticatableService {

    Authenticatable getAuthenticatableById(long id);

    long getAuthenticatableId();

    Authenticatable getAuthenticatable();

    Authenticatable getAuthenticatableByUsername(String username);

    void save(Authenticatable authenticatable);
}
