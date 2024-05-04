package com.healthcaremanagement.controller.dto;

import java.time.DayOfWeek;

public record AvailabilityDTO(
        DayOfWeek dayOfWeek,
        TimeSlotDTO timeSlot
) {
}
