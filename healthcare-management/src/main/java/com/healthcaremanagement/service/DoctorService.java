package com.healthcaremanagement.service;

import com.healthcaremanagement.controller.dto.AvailabilityDTO;
import com.healthcaremanagement.controller.dto.ContactInfoDTO;
import com.healthcaremanagement.controller.dto.DoctorDTO;
import com.healthcaremanagement.service.entity.Availability;
import com.healthcaremanagement.service.entity.Doctor;
import com.healthcaremanagement.service.mapping.AvailabilityMapping;
import com.healthcaremanagement.service.mapping.ContactInfoMapping;
import com.healthcaremanagement.service.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private DoctorDTO computeDoctorDTO(Doctor doctor) {
        ContactInfoDTO contactInfoDTO = ContactInfoMapping.INSTANCE.mapToDto(doctor.getContactInfo());
        List<Availability> availabilityList = doctor.getAvailabilityList();
        List<AvailabilityDTO> availabilityDTOList = availabilityList.stream().map(AvailabilityMapping.INSTANCE::mapToDto).toList();
        return new DoctorDTO(doctor.getName(), doctor.getSpecialty(), contactInfoDTO, availabilityDTOList);
    }

}
