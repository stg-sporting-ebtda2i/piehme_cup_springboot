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
public class AttendanceUserDTO {
    private Long id;
    private Boolean approved;
    private String description;
    private Integer coins;
    private String createdAt;
    private Date date;

    public AttendanceUserDTO(Attendance attendance) {
        this.id = attendance.getId();
        this.approved = attendance.getApproved();
        this.description = attendance.getPrice().getName();
        this.coins = attendance.getPrice().getCoins();
        this.createdAt = attendance.getCreatedAt().toString();
        this.date = attendance.getDate();
    }
}
