package com.appointmentscheduling.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AppointmentSchedulerRequest(
        @NotNull
        Long doctorId,
        @NotNull
        Long patienceId,
        @NotNull
        DayOfWeek dayOfWeek,
        @NotNull
        LocalTime time,
        @NotBlank
        String notes

) {
}
