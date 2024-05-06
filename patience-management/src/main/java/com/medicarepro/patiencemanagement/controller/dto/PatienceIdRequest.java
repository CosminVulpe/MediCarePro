package com.medicarepro.patiencemanagement.controller.dto;

import java.util.List;

public record PatienceIdRequest(
        List<String> doctorNames,
        Long patienceId
) {
}
