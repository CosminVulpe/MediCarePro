package com.medicarepro.patiencemanagement.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PatienceDTO(
        String patienceId,
        @JsonProperty("demographicInformation")
        DemographicInformationDTO demographicInformationDTO,

        @JsonProperty("contractInformation")
        ContractInformationDTO contractInformationDTO,

        @JsonProperty("insuranceInformation")
        InsuranceInformationDTO insuranceInformationDTO,

        @JsonProperty("medicalHistory")
        MedicalHistoryDTO medicalHistoryDTO,

        Long doctorId
) {
}
