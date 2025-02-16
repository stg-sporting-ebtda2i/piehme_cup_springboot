package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.PaginationDTO;
import com.stgsporting.piehmecup.entities.Admin;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.services.AdminService;
import com.stgsporting.piehmecup.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/ostaz/attendances")
    public ResponseEntity<Object> getUnapprovedAttendances(@RequestParam(required = false) Integer page) {
        SchoolYear schoolYear = adminService.getAuthenticatable().getSchoolYear();

        Pageable pageable = PageRequest.of(page == null ? 0 : page, 20);

        return ResponseEntity.ok(
                new PaginationDTO<>(attendanceService.getUnapprovedAttendances(pageable, schoolYear))
        );
    }

    @PatchMapping("ostaz/attendances/{attendanceId}")
    public ResponseEntity<Object> approveAttendance(@PathVariable Long attendanceId) {
        attendanceService.approveAttendance(attendanceId);
        return ResponseEntity.ok().body(new HashMap<>(Map.of("message", "Attendance approved")));
    }

    @DeleteMapping("ostaz/attendances/{attendanceId}")
    public ResponseEntity<Object> deleteAttendance(@PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.ok().body(new HashMap<>(Map.of("message", "Attendance deleted")));
    }

    @PatchMapping("attendance/{liturgyName}")
    public ResponseEntity<Object> requestAttendance(@PathVariable String liturgyName) {
        attendanceService.requestAttendance(liturgyName);
        return ResponseEntity.ok().body("Attendance requested");
    }
}
