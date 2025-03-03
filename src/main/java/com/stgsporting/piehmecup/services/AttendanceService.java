package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.attendances.AttendanceDTO;
import com.stgsporting.piehmecup.entities.*;
import com.stgsporting.piehmecup.exceptions.*;
import com.stgsporting.piehmecup.repositories.AttendanceRepository;
import com.stgsporting.piehmecup.repositories.PriceRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

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
        Price price = priceRepository.findPricesByName(liturgyName).orElseThrow(LiturgyNotFoundException::new);
        validateAttendance(price, date, user);
        saveAttendance(liturgyName, date, user);
    }

    private void validateAttendance(Price price, Date date, User user) {
        if (date == null) throw new InvalidAttendanceException("Date is required");

        List<Attendance> attendances = attendanceRepository.findAttendancesByUserAndPrice(user, price);

        ZoneId zoneId = ZoneId.of("Africa/Cairo");

        Timestamp timestamp = new Timestamp(date.getTime());
        LocalDateTime givenDateTime = timestamp.toInstant().
                atZone(zoneId).toLocalDateTime();

        LocalDateTime previousSunday = givenDateTime
                .with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

        LocalDateTime nextSunday = givenDateTime
                .with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        for (Attendance attendance : attendances) {

            Timestamp attendanceTimestamp = new Timestamp(attendance.getDate().getTime());
            LocalDateTime attendanceDate = attendanceTimestamp.toInstant()
                    .atZone(zoneId).toLocalDateTime();

            if (!attendanceDate.isBefore(previousSunday) && !attendanceDate.isAfter(nextSunday)) {
                throw new InvalidAttendanceException("You can't attend the same liturgy twice in the same week");
            }
        }
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

        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(AttendanceNotFoundException::new);

        if (!attendance.getUser().getSchoolYear().getId().equals(admin.getSchoolYear().getId()))
            throw new AttendanceNotFoundException();

        if (attendance.getApproved())
            throw new AttendanceAlreadyApproved("Attendance already approved");

        attendance.setApproved(true);

        Price price = attendance.getPrice();

        walletService.credit(attendance.getUser(), price.getCoins(), price.getName());

        attendanceRepository.save(attendance);
    }

    @Transactional
    public void deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(AttendanceNotFoundException::new);

        if (attendance.getApproved()) {
            Price price = attendance.getPrice();
            walletService.debit(attendance.getUser(), price.getCoins(), price.getName() + " deleted");
        }

        attendanceRepository.delete(attendance);
    }

    @Transactional
    public void deleteAttendanceForUser(Long attendanceId, User user) {
        Attendance attendance = attendanceRepository
                .findByIdForUser(attendanceId, user)
                .orElseThrow(AttendanceNotFoundException::new);

        if (attendance.getApproved()) {
            throw new AttendanceAlreadyApproved("Cannot delete approved attendance");
        }

        attendanceRepository.delete(attendance);
    }

    public Page<AttendanceDTO> getUnapprovedAttendances(Pageable pageable, SchoolYear schoolYear) {
        Page<Attendance> unapprovedAttendances = attendanceRepository
                .findByApprovedAndUserContainingSchoolYear(pageable, false, schoolYear);

        return unapprovedAttendances.map(AttendanceDTO::new);
    }

    public Page<AttendanceDTO> getApprovedAttendancesOfUser(Pageable pageable) {
        Long userId = userService.getAuthenticatableId();
        return getAttendanceDTOS(userId, pageable, true);
    }

    public Page<AttendanceDTO> getApprovedAttendancesOfUser(Pageable pageable, Long userId) {
        return getAttendanceDTOS(userId, pageable, true);
    }

    public Page<AttendanceDTO> getPendingAttendancesOfUser(Pageable pageable) {
        Long userId = userService.getAuthenticatableId();
        return getAttendanceDTOS(userId, pageable, false);
    }

    public Page<AttendanceDTO> getPendingAttendancesOfUser(Pageable pageable, Long userId) {
        return getAttendanceDTOS(userId, pageable, false);
    }

    public Page<AttendanceDTO> getAllAttendancesOfUser(Pageable pageable) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Page<Attendance> allAttendances = attendanceRepository.findAttendanceByUser(user, pageable);
        return allAttendances.map(AttendanceDTO::new);
    }

    @NotNull
    private Page<AttendanceDTO> getAttendanceDTOS(Long userId, Pageable pageable, boolean approved) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Page<Attendance> approvedAttendances = attendanceRepository
                .findByApprovedAndUser(pageable, approved, user);

        return approvedAttendances.map(AttendanceDTO::new);
    }
}
