package com.stgsporting.piehmecup.dtos.attendances;

import com.stgsporting.piehmecup.entities.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {
    private Long id;
    private Long userId;
    private String username;
    private Boolean approved;
    private String description;
    private Date date;
    private Integer coins;
    private String createdAt;

    public AttendanceDTO(Attendance attendance) {
        this.id = attendance.getId();
        this.userId = attendance.getUser().getId();
        this.username = attendance.getUser().getUsername();
        this.approved = attendance.getApproved();
        this.description = attendance.getPrice().getName();
        this.coins = attendance.getPrice().getCoins();
        this.createdAt = attendance.getCreatedAt().toString();
        this.date = attendance.getDate();
    }
}
