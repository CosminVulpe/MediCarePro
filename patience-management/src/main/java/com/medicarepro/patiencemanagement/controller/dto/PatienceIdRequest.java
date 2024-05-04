package com.medicarepro.patiencemanagement.controller.dto;

public record PatienceIdRequest(
        String doctorName,
        Long patienceId
) {
}
