package com.healthcaremanagement.controller.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record AvailabilityCheckRequest(
        Long doctorId,
        DayOfWeek dayOfWeek,
        LocalTime time,
        LocalDate date
) {
}
