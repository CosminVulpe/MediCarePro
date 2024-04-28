package com.medicarepro.patiencemanagement.service;

import com.medicarepro.patiencemanagement.controller.dto.*;
import com.medicarepro.patiencemanagement.service.entity.*;
import com.medicarepro.patiencemanagement.service.exception.PatienceIdException;
import com.medicarepro.patiencemanagement.service.mapping.ContractInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.DemographicInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.InsuranceInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.MedicalHistoryMapping;
import com.medicarepro.patiencemanagement.service.repository.PatienceRepository;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
@AllArgsConstructor
public class PatienceService {
    private final PatienceRepository patienceRepository;

    public List<PatienceDTO> getAll() {
        List<Patience> patienceList = patienceRepository.findAll();
        return patienceList.parallelStream().map(this::mapPatienceEntity).toList();
    }

    public PatienceDTO findPatienceById(Long patienceId) {
        return mapPatienceEntity(getPatienceOrThrow(patienceId));
    }

    public ResponseEntity<PatienceDTO> createPatience(PatienceRequest request) {
        try {
            Patience enrolledPatience = patienceRepository.save(mapPatienceEntity(request));
            log.info("Patience with id {} saved successfully", enrolledPatience.getPatienceId());
            return ResponseEntity.status(CREATED).body(mapPatienceEntity(enrolledPatience));
        } catch (PersistenceException e) {
            log.error("Failed to save patience", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    private Patience getPatienceOrThrow(Long patienceId) {
        return patienceRepository.findById(patienceId).orElseThrow(() -> new PatienceIdException("Patience with ID cannot be found"));
    }

    private PatienceDTO mapPatienceEntity(Patience patience) {
        ContractInformationDTO contractInformationDTO = ContractInformationMapping.INSTANCE.convertToDto(patience.getContractInformation());
        DemographicInformationDTO demographicInformationDTO = DemographicInformationMapping.INSTANCE.convertToDto(patience.getDemographicInformation());
        InsuranceInformationDTO insuranceInformationDTO = InsuranceInformationMapping.INSTANCE.convertToDto(patience.getInsuranceInformation());
        MedicalHistoryDTO medicalHistoryDTO = MedicalHistoryMapping.INSTANCE.convertToDto(patience.getMedicalHistory());
        return new PatienceDTO(patience.getPatienceId(), demographicInformationDTO, contractInformationDTO, insuranceInformationDTO, medicalHistoryDTO);
    }

    private Patience mapPatienceEntity(PatienceRequest request) {
        Patience patience = Patience.builder()
                .patienceId(UUID.randomUUID().toString())
                .build();
        ContractInformation contractInformation = ContractInformationMapping.INSTANCE.convertToEntity(request.contractInformationRequest());
        contractInformation.setPatience(patience);

        DemographicInformation demographicInformation = DemographicInformationMapping.INSTANCE.convertToEntity(request.demographicInformationRequest());
        demographicInformation.setPatience(patience);

        InsuranceInformation insuranceInformation = InsuranceInformationMapping.INSTANCE.convertToEntity(request.insuranceInformationRequest());
        insuranceInformation.setPatience(patience);

        MedicalHistory medicalHistory = MedicalHistoryMapping.INSTANCE.convertToEntity(request.medicalHistoryRequest());
        medicalHistory.setPatience(patience);

        patience.setContractInformation(contractInformation);
        patience.setDemographicInformation(demographicInformation);
        patience.setInsuranceInformation(insuranceInformation);
        patience.setMedicalHistory(medicalHistory);

        return patience;
    }

}
