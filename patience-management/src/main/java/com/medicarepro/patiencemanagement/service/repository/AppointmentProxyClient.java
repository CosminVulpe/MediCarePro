package com.medicarepro.patiencemanagement.service.repository;

import com.medicarepro.patiencemanagement.controller.dto.AppointmentSchedulerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "appointment-scheduling", url = "${appointment.scheduling.port}")
public interface AppointmentProxyClient {
    String APPOINTMENT_API = "/api/appointment";

    @PostMapping(APPOINTMENT_API + "/schedule")
    ResponseEntity<HttpStatus> scheduleAppointment(@RequestBody AppointmentSchedulerRequest request);
}
