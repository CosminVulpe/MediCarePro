package com.healthcaremanagement.controller;

import com.healthcaremanagement.controller.dto.DoctorDTO;
import com.healthcaremanagement.controller.dto.DoctorIdResponse;
import com.healthcaremanagement.controller.dto.PatienceIdRequest;
import com.healthcaremanagement.service.DoctorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/assign")
    public ResponseEntity<DoctorIdResponse> assignPatience(@RequestBody @Valid PatienceIdRequest request) {
        return doctorService.assignPatience(request);
    }

}
