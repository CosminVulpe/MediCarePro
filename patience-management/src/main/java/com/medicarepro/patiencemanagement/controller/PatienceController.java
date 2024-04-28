package com.medicarepro.patiencemanagement.controller;

import com.medicarepro.patiencemanagement.controller.dto.PatienceDTO;
import com.medicarepro.patiencemanagement.service.PatienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    
}
