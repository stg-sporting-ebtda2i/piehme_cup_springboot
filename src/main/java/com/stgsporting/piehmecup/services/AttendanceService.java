package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.attendances.AttendanceDTO;
import com.stgsporting.piehmecup.entities.*;
import com.stgsporting.piehmecup.exceptions.AttendanceAlreadyApproved;
import com.stgsporting.piehmecup.exceptions.AttendanceNotFoundException;
import com.stgsporting.piehmecup.exceptions.LiturgyNotFoundException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.AttendanceRepository;
import com.stgsporting.piehmecup.repositories.PriceRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PriceRepository priceRepository;
    private final WalletService walletService;
    private final PriceService priceService;
    private final AdminService adminService;

    public AttendanceService(AttendanceRepository attendanceRepository, UserService userService, UserRepository userRepository, PriceRepository priceRepository, WalletService walletService, PriceService priceService, AdminService adminService) {
        this.attendanceRepository = attendanceRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.priceRepository = priceRepository;
        this.walletService = walletService;
        this.priceService = priceService;
        this.adminService = adminService;
    }

    public void requestAttendance(String liturgyName, Date date) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        priceRepository.findPricesByName(liturgyName).orElseThrow(LiturgyNotFoundException::new);

        saveAttendance(liturgyName, date, user);
    }

    private void saveAttendance(String liturgyName, Date date, User user) {
        Attendance attendance = new Attendance();
        attendance.setPrice(priceService.getPrice(liturgyName));
        attendance.setUser(user);
        attendance.setDate(date);
        attendance.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        attendance.setApproved(false);
        attendanceRepository.save(attendance);
    }

    @Transactional
    public void approveAttendance(Long attendanceId) {
        Authenticatable admin = adminService.getAuthenticatable();

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found"));

        if (!attendance.getUser().getSchoolYear().getId().equals(admin.getSchoolYear().getId()))
            throw new AttendanceNotFoundException("Attendance not found");

        if (attendance.getApproved())
            throw new AttendanceAlreadyApproved("Attendance already approved");

        attendance.setApproved(true);

        Price price = attendance.getPrice();

        walletService.credit(attendance.getUser(), price.getCoins(), price.getName());

        attendanceRepository.save(attendance);
    }

    @Transactional
    public void deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found"));

        if (attendance.getApproved()) {
            Price price = attendance.getPrice();
            walletService.debit(attendance.getUser(), price.getCoins(), price.getName() + " deleted");
        }

        attendanceRepository.delete(attendance);
    }

    public Page<AttendanceDTO> getUnapprovedAttendances(Pageable pageable, SchoolYear schoolYear) {
        Page<Attendance> unapprovedAttendances = attendanceRepository
                .findByApprovedAndUserContainingSchoolYear(pageable, false, schoolYear);

        return unapprovedAttendances.map(AttendanceDTO::new);
    }
}
