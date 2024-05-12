package com.healthcaremanagement.controller.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PatienceIdRequest(
        @NotEmpty
        List<String> doctorNames,

        @NotNull
        Long patienceId
) {
}
