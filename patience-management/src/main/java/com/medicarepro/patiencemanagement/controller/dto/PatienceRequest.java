package com.medicarepro.patiencemanagement.controller.dto;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PatienceRequest(
        @NotNull
        ContractInformationDTO contractInformationRequest,

        @NotNull
        DemographicInformationDTO demographicInformationRequest,

        @NotNull
        InsuranceInformationDTO insuranceInformationRequest,

        @NotNull
        MedicalHistoryDTO medicalHistoryRequest,

        @NotNull
        List<String> doctorNames
) {
}
