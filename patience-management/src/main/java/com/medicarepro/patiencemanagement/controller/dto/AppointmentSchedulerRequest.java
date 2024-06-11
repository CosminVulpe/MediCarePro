package com.medicarepro.patiencemanagement.controller.dto;


import java.time.DayOfWeek;
import java.time.LocalTime;

public record AppointmentSchedulerRequest(
        Long doctorId,
        Long patienceId,
        DayOfWeek dayOfWeek,
        LocalTime time,
        String notes
) {
}
