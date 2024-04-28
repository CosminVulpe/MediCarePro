package com.medicarepro.patiencemanagement.controller;

import com.medicarepro.patiencemanagement.controller.dto.PatienceDTO;
import com.medicarepro.patiencemanagement.controller.dto.PatienceRequest;
import com.medicarepro.patiencemanagement.service.PatienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patience")
public class PatienceController {
    private final PatienceService patienceService;

    @GetMapping
    public List<PatienceDTO> getAllPatience() {
        return patienceService.getAll();
    }

    @GetMapping("/{patienceId}")
    public PatienceDTO getAllPatience(@PathVariable Long patienceId) {
        return patienceService.findPatienceById(patienceId);
    }

    @PostMapping("/create")
    public ResponseEntity<PatienceDTO> createPatience(@RequestBody @Valid PatienceRequest request) {
        return patienceService.createPatience(request);
    }

    @PatchMapping("/update/{patienceId}")
    public void updatePatience(@PathVariable Long patienceId,@RequestBody PatienceRequest request) {
        patienceService.updatePatience(patienceId, request);
    }

    @DeleteMapping("/delete/{patienceId}")
    public void deletePatience(@PathVariable Long patienceId) {
        patienceService.deletePatienceById(patienceId);
    }

}
