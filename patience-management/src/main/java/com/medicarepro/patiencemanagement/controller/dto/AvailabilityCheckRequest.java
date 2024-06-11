package com.medicarepro.patiencemanagement.controller.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityCheckRequest(
        Long doctorId,
        DayOfWeek dayOfWeek,
        LocalTime time
) {
}
