package com.healthcaremanagement.service;

import com.healthcaremanagement.controller.dto.*;
import com.healthcaremanagement.service.entity.Availability;
import com.healthcaremanagement.service.entity.Doctor;
import com.healthcaremanagement.service.entity.TimeSlot;
import com.healthcaremanagement.service.exception.DoctorNotFoundByIdException;
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
import java.util.Optional;

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

    public Boolean checkAvailability(AvailabilityCheckRequest request) {
        Doctor doctorOrThrow = getDoctorOrThrow(request.doctorId());
        List<Availability> availabilityList = doctorOrThrow.getAvailabilityList();

        Optional<Availability> availabilityOptional = availabilityList.stream()
                .filter(availability -> availability.getDayOfWeek().equals(request.dayOfWeek()))
                .filter(availability -> !request.time().isBefore(availability.getTimeSlot().getStartTime())
                        && !request.time().isAfter(availability.getTimeSlot().getEndTime()))
                .filter(availability -> request.time().equals(availability.getTimeSlot().getStartTime())
                        || request.time().equals(availability.getTimeSlot().getEndTime()))
                .findFirst();

        return availabilityOptional.isPresent();
    }

    private DoctorDTO computeDoctorDTO(Doctor doctor) {
        ContactInfoDTO contactInfoDTO = ContactInfoMapping.INSTANCE.mapToDto(doctor.getContactInfo());
        List<Availability> availabilityList = doctor.getAvailabilityList();
        List<AvailabilityDTO> availabilityDTOList = availabilityList.stream().map(AvailabilityMapping.INSTANCE::mapToDto).toList();
        return new DoctorDTO(doctor.getName(), doctor.getSpecialty(), contactInfoDTO, availabilityDTOList);
    }

    private Doctor getDoctorOrThrow(Long doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundByIdException("Doctor with ID provided was not found"));
    }


}
