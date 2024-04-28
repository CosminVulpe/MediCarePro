package com.medicarepro.patiencemanagement.service;

import com.medicarepro.patiencemanagement.controller.dto.*;
import com.medicarepro.patiencemanagement.service.entity.Patience;
import com.medicarepro.patiencemanagement.service.exception.PatienceIdException;
import com.medicarepro.patiencemanagement.service.mapping.ContractInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.DemographicInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.InsuranceInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.MedicalHistoryMapping;
import com.medicarepro.patiencemanagement.service.repository.PatienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

}
