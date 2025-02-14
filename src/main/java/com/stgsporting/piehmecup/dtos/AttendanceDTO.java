package com.stgsporting.piehmecup.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {
    private Long attendanceId;
    private Long userId;
    private String username;
    private Boolean approved;
    private String liturgyName;
    private String createdAt;
}
