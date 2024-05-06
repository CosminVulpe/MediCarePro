package com.healthcaremanagement.controller.dto;

import java.util.List;

public record PatienceIdRequest(
        List<String> doctorNames,
        Long patienceId
) {
}
