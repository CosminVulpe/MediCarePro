package com.appointmentscheduling.controller;

import com.appointmentscheduling.controller.dto.AppointmentByIdRequest;
import com.appointmentscheduling.controller.dto.AppointmentResponse;
import com.appointmentscheduling.controller.dto.AppointmentSchedulerRequest;
import com.appointmentscheduling.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
@AllArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<AppointmentResponse> getAppointmentByPatienceId(@RequestBody AppointmentByIdRequest request) {
        return appointmentService.getAppointmentByDoctorIdOrPatienceId(request);
    }

    @PostMapping("/schedule")
    public ResponseEntity<HttpStatus> scheduleAppointment(@RequestBody AppointmentSchedulerRequest request) {
        return appointmentService.scheduleAppointment(request);
    }
}
