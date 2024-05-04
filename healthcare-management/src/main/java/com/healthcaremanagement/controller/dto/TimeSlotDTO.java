package com.healthcaremanagement.controller.dto;

import java.time.LocalTime;

public record TimeSlotDTO(
        LocalTime startTime,
        LocalTime endTime
) {
}
