package com.stgsporting.piehmecup.exceptions;

public class AttendanceNotFoundException extends NotFoundException {
    public AttendanceNotFoundException(String message) {
        super(message);
    }

    public AttendanceNotFoundException() {
        super("Attendance not found");
    }
}
