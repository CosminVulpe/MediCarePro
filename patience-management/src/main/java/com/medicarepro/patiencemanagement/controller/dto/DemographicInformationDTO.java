package com.medicarepro.patiencemanagement.controller.dto;

import com.medicarepro.patiencemanagement.service.Gender;

import java.time.LocalDate;

public record DemographicInformationDTO(
        String name,
        int age,
        Gender gender,
        LocalDate timeOfBirth,
        boolean married,
        boolean hasKids
) {
}
