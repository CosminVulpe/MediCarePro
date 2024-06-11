package com.medicarepro.patiencemanagement.service.repository;

import com.medicarepro.patiencemanagement.controller.dto.AvailabilityCheckRequest;
import com.medicarepro.patiencemanagement.controller.dto.DoctorIdResponse;
import com.medicarepro.patiencemanagement.controller.dto.PatienceIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "healthcare-management", url = "${healthcare.management.port}")
public interface HealthcareProxyClient {
    String url = "/api/healthcare";

    @PostMapping(url + "/assign")
    ResponseEntity<DoctorIdResponse> assignPatience(@RequestBody PatienceIdRequest request);

    @PostMapping(url + "/availability")
    Boolean checkAvailability(AvailabilityCheckRequest request);
}
