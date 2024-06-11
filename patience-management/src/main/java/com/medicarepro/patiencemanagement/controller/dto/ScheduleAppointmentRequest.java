package com.medicarepro.patiencemanagement.controller.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleAppointmentRequest(
        LocalTime time,
        DayOfWeek dayOfWeek,

        String notes,
        Long doctorId,
        Long patienceId
) {
}
