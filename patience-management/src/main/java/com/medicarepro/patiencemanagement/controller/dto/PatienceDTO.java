package com.medicarepro.patiencemanagement.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

        List<Long> doctorIds
) {
}
