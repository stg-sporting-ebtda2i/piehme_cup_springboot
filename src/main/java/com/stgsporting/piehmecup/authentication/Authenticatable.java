package com.stgsporting.piehmecup.authentication;

import com.stgsporting.piehmecup.entities.SchoolYear;

public interface Authenticatable {
    Long getId();
    String getUsername();
    String getPassword();
    SchoolYear getSchoolYear();
    String getRoleString();
}
