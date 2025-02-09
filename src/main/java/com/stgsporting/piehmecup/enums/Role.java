package com.stgsporting.piehmecup.enums;

import java.util.Optional;

public enum Role {
    ADMIN,
    OSTAZ;

    static public Optional<Role> lookup(String id) {
        try {
            return Optional.of(Role.valueOf(id));
        }catch (IllegalArgumentException e){
            return Optional.empty();
        }
    }
}