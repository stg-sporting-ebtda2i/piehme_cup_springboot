package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.authentication.Authenticatable;
import com.stgsporting.piehmecup.dtos.AttendanceDTO;
import com.stgsporting.piehmecup.entities.*;
import com.stgsporting.piehmecup.exceptions.AttendanceAlreadyApproved;
import com.stgsporting.piehmecup.exceptions.AttendanceNotFoundException;
import com.stgsporting.piehmecup.exceptions.LiturgyNotFound;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.AttendanceRepository;
import com.stgsporting.piehmecup.repositories.PriceRepository;
import com.stgsporting.piehmecup.repositories.SchoolYearRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private AdminService adminService;

    public void requestAttendance(String liturgyName) {
        try {
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            priceRepository.findPricesByName(liturgyName)
                    .orElseThrow(() -> new LiturgyNotFound("Liturgy not found"));

            saveAttendance(liturgyName, user);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to request attendance");
        }
    }

    private void saveAttendance(String liturgyName, User user) {
        Attendance attendance = new Attendance();
        attendance.setPrice(priceService.getPrice(liturgyName));
        attendance.setUser(user);
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

        attendanceRepository.delete(attendance);
    }

    public List<AttendanceDTO> getUnapprovedAttendances(SchoolYear schoolYear) {
        List<Attendance> unapprovedAttendances = attendanceRepository
                .findByApprovedAndUserContainingSchoolYear(false, schoolYear);

        return getAttendanceDTOS(unapprovedAttendances);
    }

    @NotNull
    private static List<AttendanceDTO> getAttendanceDTOS(List<Attendance> unapprovedAttendances) {
        List<AttendanceDTO> dtos = new ArrayList<>();
        for (Attendance attendance : unapprovedAttendances) {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setAttendanceId(attendance.getId());
            dto.setUserId(attendance.getUser().getId());
            dto.setUsername(attendance.getUser().getUsername());
            dto.setApproved(attendance.getApproved());
            dto.setLiturgyName(attendance.getPrice().getName());
            dto.setCreatedAt(attendance.getCreatedAt().toString());
            dtos.add(dto);
        }
        return dtos;
    }

    private static String getTimeStampFormat(Attendance attendance) {
        Timestamp timestamp = attendance.getCreatedAt();
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        if (dateTime.toLocalDate().isEqual(now.toLocalDate()))
            return "today at " + dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        else if (dateTime.isAfter(now.minusWeeks(1)))
            return dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                    + " at " + dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        else
            return dateTime.format(DateTimeFormatter.ofPattern("dd/MM 'at' HH:mm"));
    }
}
