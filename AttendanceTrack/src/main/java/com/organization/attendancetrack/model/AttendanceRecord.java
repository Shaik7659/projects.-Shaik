package com.organization.attendancetrack.model;

import java.time.LocalDateTime;


public record AttendanceRecord(Long employeeId, String SwipeType,LocalDateTime officeInTime, LocalDateTime officeOutTime) {
}
