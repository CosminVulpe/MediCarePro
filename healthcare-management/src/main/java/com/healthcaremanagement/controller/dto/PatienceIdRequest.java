package com.healthcaremanagement.controller.dto;

public record PatienceIdRequest(
        String doctorName,
        Long patienceId
) {
}
