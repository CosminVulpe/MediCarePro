package com.healthcaremanagement.controller.dto;

import com.healthcaremanagement.service.entity.DoctorSpecialty;

import java.util.List;

public record DoctorDTO(
        String name
        , DoctorSpecialty specialty
        , ContactInfoDTO contactInfo
        , List<AvailabilityDTO> availabilityList
) {
}
