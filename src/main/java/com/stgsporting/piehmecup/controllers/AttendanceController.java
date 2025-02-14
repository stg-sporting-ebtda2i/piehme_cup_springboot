package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/ostaz/attendances/{schoolYear}")
    public ResponseEntity<Object> getUnapprovedAttendances(@PathVariable Long schoolYear) {
        try {
            return ResponseEntity.ok(attendanceService.getUnapprovedAttendances(schoolYear));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("ostaz/attendances/{attendanceId}")
    public ResponseEntity<Object> approveAttendance(@PathVariable Long attendanceId) {
        try {
            attendanceService.approveAttendance(attendanceId);
            return ResponseEntity.ok().body("Attendance approved");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("ostaz/attendances/{attendanceId}")
    public ResponseEntity<Object> deleteAttendance(@PathVariable Long attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return ResponseEntity.ok().body("Attendance deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("attendance/{liturgyName}")
    public ResponseEntity<Object> requestAttendance(@PathVariable String liturgyName) {
        try {
            attendanceService.requestAttendance(liturgyName);
            return ResponseEntity.ok().body("Attendance requested");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
