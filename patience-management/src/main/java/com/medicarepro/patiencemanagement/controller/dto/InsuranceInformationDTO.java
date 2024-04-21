package com.medicarepro.patiencemanagement.controller.dto;

import java.time.LocalDate;

public record InsuranceInformationDTO(
        String insuranceProvider,
        Long insurancePolicyNumber,
        LocalDate coverageDateStart,
        LocalDate coverageDateEnd
) {
}
