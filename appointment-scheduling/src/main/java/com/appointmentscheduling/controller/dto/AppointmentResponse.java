package com.appointmentscheduling.controller.dto;

import com.appointmentscheduling.service.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponse(
        LocalDate date,
        LocalTime time,
        Status status,
        String notes
) {
}
