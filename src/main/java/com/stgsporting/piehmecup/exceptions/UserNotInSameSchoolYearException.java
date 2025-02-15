package com.stgsporting.piehmecup.exceptions;

public class UserNotInSameSchoolYearException extends NotFoundException {
    public UserNotInSameSchoolYearException() {
        super("User is not in the same school year");
    }
}
