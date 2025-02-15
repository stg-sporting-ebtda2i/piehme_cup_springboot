package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.services.AdminService;
import com.stgsporting.piehmecup.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/ostaz/attendances")
    public ResponseEntity<Object> getUnapprovedAttendances() {
        SchoolYear schoolYear = adminService.getAuthenticatable().getSchoolYear();

        return ResponseEntity.ok(attendanceService.getUnapprovedAttendances(schoolYear));
    }

    @PatchMapping("ostaz/attendances/{attendanceId}")
    public ResponseEntity<Object> approveAttendance(@PathVariable Long attendanceId) {
        attendanceService.approveAttendance(attendanceId);
        return ResponseEntity.ok().body("Attendance approved");
    }

    @DeleteMapping("ostaz/attendances/{attendanceId}")
    public ResponseEntity<Object> deleteAttendance(@PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.ok().body("Attendance deleted");
    }

    @PatchMapping("attendance/{liturgyName}")
    public ResponseEntity<Object> requestAttendance(@PathVariable String liturgyName) {
        attendanceService.requestAttendance(liturgyName);
        return ResponseEntity.ok().body("Attendance requested");
    }
}
