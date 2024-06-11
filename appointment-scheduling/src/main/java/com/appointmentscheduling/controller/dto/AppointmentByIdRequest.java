package com.appointmentscheduling.controller.dto;

import jakarta.annotation.Nullable;

public record AppointmentByIdRequest(
        @Nullable
        Long doctorId,

        @Nullable
        Long patienceId
) {
}
