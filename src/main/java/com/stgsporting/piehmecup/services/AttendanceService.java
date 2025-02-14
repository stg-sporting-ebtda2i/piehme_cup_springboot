package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.AttendanceDTO;
import com.stgsporting.piehmecup.entities.Attendance;
import com.stgsporting.piehmecup.entities.Price;
import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.entities.User;
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
    private SchoolYearRepository schoolYearRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private WalletService walletService;

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
        attendance.setLiturgyName(liturgyName);
        attendance.setUser(user);
        attendance.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        attendance.setApproved(false);
        attendanceRepository.save(attendance);
    }

    @Transactional
    public void approveAttendance(Long attendanceId) {
        try {
            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found"));
            if (attendance.getApproved())
                throw new AttendanceAlreadyApproved("Attendance already approved");
            attendance.setApproved(true);

            Price price = priceRepository.findPricesByName(attendance.getLiturgyName())
                    .orElseThrow(() -> new LiturgyNotFound("Liturgy not found"));
            walletService.credit(attendance.getUser(), price.getCoins(), attendance.getLiturgyName());

            attendanceRepository.save(attendance);
        } catch (AttendanceNotFoundException | LiturgyNotFound | AttendanceAlreadyApproved e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to approve attendance");
        }
    }

    @Transactional
    public void deleteAttendance(Long attendanceId) {
        try {
            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found"));

            attendanceRepository.delete(attendance);
        } catch (AttendanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete attendance");
        }
    }

    public List<AttendanceDTO> getUnapprovedAttendances(Long schoolYear) {
        try {
            SchoolYear schoolYearEntity = schoolYearRepository.findSchoolYearById(schoolYear)
                    .orElseThrow(() -> new RuntimeException("School year not found"));
            List<Attendance> unapprovedAttendances = attendanceRepository.findByApprovedAndUserContainingSchoolYear(false, schoolYearEntity);

            return getAttendanceDTOS(unapprovedAttendances);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get unapproved attendances");
        }
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
            dto.setLiturgyName(attendance.getLiturgyName());
            dto.setCreatedAt(getTimeStampFormat(attendance));
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
