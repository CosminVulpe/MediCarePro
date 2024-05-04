package com.healthcaremanagement.controller;

import com.healthcaremanagement.controller.dto.DoctorDTO;
import com.healthcaremanagement.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/healthcare")
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getAll() {
        return doctorService.getAllDoctors();
    }

}
