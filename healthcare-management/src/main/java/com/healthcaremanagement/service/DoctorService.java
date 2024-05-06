package com.healthcaremanagement.service;

import com.healthcaremanagement.controller.dto.*;
import com.healthcaremanagement.service.entity.Availability;
import com.healthcaremanagement.service.entity.Doctor;
import com.healthcaremanagement.service.mapping.AvailabilityMapping;
import com.healthcaremanagement.service.mapping.ContactInfoMapping;
import com.healthcaremanagement.service.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::computeDoctorDTO).toList();
    }

    public ResponseEntity<DoctorIdResponse> assignPatience(PatienceIdRequest request) {
        List<Doctor> doctorList = request.doctorNames().stream().map(doctorRepository::findDoctorsByName)
                .flatMap(Collection::stream).toList();

        if (doctorList.isEmpty()) {
            log.warn("No doctor found with name provided");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        doctorList.forEach(doctor -> {
            if (doctor.getPatienceIds().isEmpty()) {
                doctor.setPatienceIds(List.of(request.patienceId()));
            } else {
                doctor.getPatienceIds().add(request.patienceId());
            }
            doctorRepository.save(doctor);
        });

        return ResponseEntity.ok(new DoctorIdResponse(doctorList.stream().map(Doctor::getId).toList()));
    }

    private DoctorDTO computeDoctorDTO(Doctor doctor) {
        ContactInfoDTO contactInfoDTO = ContactInfoMapping.INSTANCE.mapToDto(doctor.getContactInfo());
        List<Availability> availabilityList = doctor.getAvailabilityList();
        List<AvailabilityDTO> availabilityDTOList = availabilityList.stream().map(AvailabilityMapping.INSTANCE::mapToDto).toList();
        return new DoctorDTO(doctor.getName(), doctor.getSpecialty(), contactInfoDTO, availabilityDTOList);
    }

}
