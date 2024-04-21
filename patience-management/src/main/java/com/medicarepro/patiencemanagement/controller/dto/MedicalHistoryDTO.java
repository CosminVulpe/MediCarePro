package com.medicarepro.patiencemanagement.controller.dto;

import java.util.List;

public record MedicalHistoryDTO(
        List<String> conditions,
        List<String> allergies,
        List<String> medications,
        List<String> surgeries
) {
}
