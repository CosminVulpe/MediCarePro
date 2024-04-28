package com.medicarepro.patiencemanagement.controller.dto;


import jakarta.validation.constraints.NotNull;

public record PatienceRequest(
        @NotNull
        ContractInformationDTO contractInformationRequest,

        @NotNull
        DemographicInformationDTO demographicInformationRequest,

        @NotNull
        InsuranceInformationDTO insuranceInformationRequest,

        @NotNull
        MedicalHistoryDTO medicalHistoryRequest
) {
}
