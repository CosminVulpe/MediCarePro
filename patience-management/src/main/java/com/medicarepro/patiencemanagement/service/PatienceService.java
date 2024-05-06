package com.medicarepro.patiencemanagement.service;

import com.medicarepro.patiencemanagement.controller.dto.*;
import com.medicarepro.patiencemanagement.service.entity.*;
import com.medicarepro.patiencemanagement.service.exception.PatienceIdException;
import com.medicarepro.patiencemanagement.service.mapping.ContractInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.DemographicInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.InsuranceInformationMapping;
import com.medicarepro.patiencemanagement.service.mapping.MedicalHistoryMapping;
import com.medicarepro.patiencemanagement.service.repository.HealthcareProxyClient;
import com.medicarepro.patiencemanagement.service.repository.PatienceRepository;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;

@Service
@Slf4j
@AllArgsConstructor
public class PatienceService {
    private final PatienceRepository patienceRepository;
    private final HealthcareProxyClient healthcareProxyClient;

    public List<PatienceDTO> getAll() {
        List<Patience> patienceList = patienceRepository.findAll();
        return patienceList.stream().map(patience -> mapPatienceEntity(patience, patience.getDoctorIds())).toList();
    }

    public PatienceDTO findPatienceById(Long patienceId) {
        Patience patience = getPatienceOrThrow(patienceId);
        return mapPatienceEntity(patience, patience.getDoctorIds());
    }

    public ResponseEntity<PatienceDTO> createPatience(PatienceRequest request) {
        int doesPatienceExist = patienceRepository.doesPatienceExist(request.demographicInformationRequest().name());

        if (doesPatienceExist >= 1) {
            log.warn("Patience is already in our DB");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        try {
            Patience enrolledPatience = patienceRepository.save(mapPatienceEntity(request));

            ResponseEntity<DoctorIdResponse> doctorIdResponseResponseEntity = healthcareProxyClient.assignPatience(
                    new PatienceIdRequest(request.doctorNames(), enrolledPatience.getId())
            );

            if (doctorIdResponseResponseEntity.getStatusCode() != HttpStatus.NO_CONTENT
                    && Objects.nonNull(doctorIdResponseResponseEntity.getBody())) {

                DoctorIdResponse response = doctorIdResponseResponseEntity.getBody();
                enrolledPatience.setDoctorIds(response.ids());
                patienceRepository.save(enrolledPatience);
            }

            log.info("Patience with patienceId {} saved successfully", enrolledPatience.getPatienceId());
            return ResponseEntity.status(CREATED).body(mapPatienceEntity(enrolledPatience, enrolledPatience.getDoctorIds()));
        } catch (PersistenceException e) {
            log.error("Failed to save patience", e);
            throw e;
        }
    }

    public void updatePatience(Long patienceId, PatienceRequest request) {
        Patience patienceOrThrow = getPatienceOrThrow(patienceId);
        log.info("Info updated for patienceId {} ", patienceOrThrow.getPatienceId());
        updatePatienceDetails(request, patienceOrThrow);
    }

    public void deletePatienceById(Long patienceId) {
        Patience patience = getPatienceOrThrow(patienceId);
        log.info("Patience with patienceId {} was deleted", patience.getPatienceId());
        patienceRepository.delete(patience);
    }

    private Patience getPatienceOrThrow(Long patienceId) {
        return patienceRepository.findById(patienceId).orElseThrow(() -> new PatienceIdException("Patience with ID cannot be found"));
    }

    private PatienceDTO mapPatienceEntity(Patience patience, List<Long> doctorIds) {
        ContractInformationDTO contractInformationDTO = ContractInformationMapping.INSTANCE.convertToDto(patience.getContractInformation());
        DemographicInformationDTO demographicInformationDTO = DemographicInformationMapping.INSTANCE.convertToDto(patience.getDemographicInformation());
        InsuranceInformationDTO insuranceInformationDTO = InsuranceInformationMapping.INSTANCE.convertToDto(patience.getInsuranceInformation());
        MedicalHistoryDTO medicalHistoryDTO = MedicalHistoryMapping.INSTANCE.convertToDto(patience.getMedicalHistory());
        List<Long> doctors = (doctorIds == null || doctorIds.isEmpty()) ? Collections.emptyList() : doctorIds;

        return new PatienceDTO(patience.getPatienceId(), demographicInformationDTO
                , contractInformationDTO, insuranceInformationDTO, medicalHistoryDTO, doctors);
    }

    private Patience mapPatienceEntity(PatienceRequest request) {
        Patience patience = Patience.builder()
                .patienceId(generatePatienceId())
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

    private void updatePatienceDetails(PatienceRequest request, Patience patience) {
        ContractInformation contractInformation = patience.getContractInformation();
        MedicalHistory medicalHistory = patience.getMedicalHistory();
        InsuranceInformation insuranceInformation = patience.getInsuranceInformation();
        DemographicInformation demographicInformation = patience.getDemographicInformation();

        ContractInformationDTO contractInformationReq = request.contractInformationRequest();
        MedicalHistoryDTO medicalHistoryReq = request.medicalHistoryRequest();
        InsuranceInformationDTO insuranceInformationReq = request.insuranceInformationRequest();
        DemographicInformationDTO demographicInformationReq = request.demographicInformationRequest();

        patience.setContractInformation(getUpdateContractInfo(contractInformation, contractInformationReq));
        patience.setMedicalHistory(getUpdateMedicalHistory(medicalHistory, medicalHistoryReq));
        patience.setInsuranceInformation(getUpdateInsuranceInfo(insuranceInformation, insuranceInformationReq));
        patience.setDemographicInformation(getUpdateDemographicInfo(demographicInformation, demographicInformationReq));

        patienceRepository.save(patience);
    }

    private ContractInformation getUpdateContractInfo(ContractInformation contractInformation, ContractInformationDTO contractInformationReq) {
        if (nonNull(contractInformationReq)) {
            if (checkInfo(contractInformationReq.address())) {
                contractInformation.setAddress(contractInformationReq.address());
            }

            if (checkInfo(contractInformationReq.phoneNumber())) {
                contractInformation.setPhoneNumber(contractInformationReq.phoneNumber());
            }

            if (checkInfo(contractInformationReq.email())) {
                contractInformation.setEmail(contractInformationReq.email());
            }
        }
        return contractInformation;
    }

    private MedicalHistory getUpdateMedicalHistory(MedicalHistory medicalHistory, MedicalHistoryDTO medicalHistoryReq) {
        if (nonNull(medicalHistoryReq)) {
            if (checkInfo(medicalHistoryReq.conditions())) {
                medicalHistory.setConditions(medicalHistoryReq.conditions());
            }

            if (checkInfo(medicalHistoryReq.allergies())) {
                medicalHistory.setAllergies(medicalHistoryReq.allergies());
            }

            if (checkInfo(medicalHistoryReq.medications())) {
                medicalHistory.setMedications(medicalHistoryReq.medications());
            }

            if (checkInfo(medicalHistoryReq.surgeries())) {
                medicalHistory.setSurgeries(medicalHistoryReq.surgeries());
            }
        }
        return medicalHistory;
    }

    private InsuranceInformation getUpdateInsuranceInfo(InsuranceInformation insuranceInformation, InsuranceInformationDTO insuranceInformationReq) {
        if (nonNull(insuranceInformationReq)) {
            if (checkInfo(insuranceInformationReq.insuranceProvider())) {
                insuranceInformation.setInsuranceProvider(insuranceInformationReq.insuranceProvider());
            }

            if (insuranceInformationReq.insurancePolicyNumber() != null && !insuranceInformationReq.insurancePolicyNumber().equals(0)) {
                insuranceInformation.setInsurancePolicyNumber(insuranceInformationReq.insurancePolicyNumber());
            }

            LocalDate coverageDateStart = insuranceInformationReq.coverageDateStart();
            LocalDate coverageDateEnd = insuranceInformationReq.coverageDateEnd();
            if (checkInfo(coverageDateStart) && checkInfo(coverageDateEnd)) {
                if (coverageDateStart.isBefore(coverageDateEnd)) {
                    insuranceInformation.setCoverageDateStart(coverageDateStart);
                }

                if (coverageDateEnd.isAfter(coverageDateStart)) {
                    insuranceInformation.setCoverageDateEnd(coverageDateEnd);
                }
            }
        }
        return insuranceInformation;
    }

    private DemographicInformation getUpdateDemographicInfo(DemographicInformation demographicInformation, DemographicInformationDTO demographicInformationReq) {
        if (nonNull(demographicInformationReq)) {
            if (checkInfo(demographicInformationReq.name())) {
                demographicInformation.setName(demographicInformationReq.name());
            }

            if (demographicInformationReq.age() > 0 && demographicInformationReq.age() != demographicInformation.getAge()) {
                demographicInformation.setAge(demographicInformationReq.age());
            }

            LocalDate timeOfBirth = demographicInformationReq.timeOfBirth();
            if (checkInfo(timeOfBirth) && timeOfBirth.isBefore(LocalDate.now())) {
                demographicInformation.setTimeOfBirth(demographicInformationReq.timeOfBirth());
            }

            if (demographicInformationReq.married() != demographicInformation.isMarried()) {
                demographicInformation.setMarried(demographicInformationReq.married());
            }

            if (demographicInformationReq.hasKids() != demographicInformation.isHasKids()) {
                demographicInformation.setHasKids(demographicInformationReq.hasKids());
            }
        }
        return demographicInformation;
    }

    private boolean checkInfo(String value) {
        return value != null && !value.isBlank();
    }

    private boolean checkInfo(LocalDate value) {
        return value != null;
    }

    private boolean checkInfo(List<String> value) {
        return value != null && !value.isEmpty();
    }

    private String generatePatienceId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
