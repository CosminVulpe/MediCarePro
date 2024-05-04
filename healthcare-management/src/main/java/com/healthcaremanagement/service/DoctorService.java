package com.healthcaremanagement.service;

import com.healthcaremanagement.controller.dto.*;
import com.healthcaremanagement.service.entity.Availability;
import com.healthcaremanagement.service.entity.Doctor;
import com.healthcaremanagement.service.mapping.AvailabilityMapping;
import com.healthcaremanagement.service.mapping.ContactInfoMapping;
import com.healthcaremanagement.service.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        List<Doctor> doctorsByName = doctorRepository.findDoctorsByName(request.doctorName());
        if (doctorsByName.isEmpty()) {
            log.warn("No doctor found with name provided");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Doctor doctor = doctorsByName.stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        doctor.getPatienceIds().add(request.patienceId());
        doctorRepository.save(doctor);
        return ResponseEntity.ok(new DoctorIdResponse(doctor.getId()));
    }

    private DoctorDTO computeDoctorDTO(Doctor doctor) {
        ContactInfoDTO contactInfoDTO = ContactInfoMapping.INSTANCE.mapToDto(doctor.getContactInfo());
        List<Availability> availabilityList = doctor.getAvailabilityList();
        List<AvailabilityDTO> availabilityDTOList = availabilityList.stream().map(AvailabilityMapping.INSTANCE::mapToDto).toList();
        return new DoctorDTO(doctor.getName(), doctor.getSpecialty(), contactInfoDTO, availabilityDTOList);
    }

}