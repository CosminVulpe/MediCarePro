package com.appointmentscheduling.service;

import com.appointmentscheduling.controller.dto.AppointmentByIdRequest;
import com.appointmentscheduling.controller.dto.AppointmentResponse;
import com.appointmentscheduling.controller.dto.AppointmentSchedulerRequest;
import com.appointmentscheduling.service.entity.Appointment;
import com.appointmentscheduling.service.mapping.AppointmentMapping;
import com.appointmentscheduling.service.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppointmentService {
    private final AppointmentRepository repository;

    public ResponseEntity<AppointmentResponse> getAppointmentByDoctorIdOrPatienceId(AppointmentByIdRequest request) {
        Optional<Appointment> appointmentByDoctorIdOrPatienceId = repository.findAppointmentByDoctorIdOrPatienceId(request.doctorId(), request.patienceId());
        if (appointmentByDoctorIdOrPatienceId.isEmpty()) {
            log.warn("No appointment was found for patienceId {} or doctorId {}", request.patienceId(), request.doctorId());
            return ResponseEntity.noContent().build();
        }

        AppointmentResponse appointmentResponse = AppointmentMapping.INSTANCE.mapToDto(appointmentByDoctorIdOrPatienceId.get());
        return ResponseEntity.ok(appointmentResponse);
    }

    public ResponseEntity<HttpStatus> scheduleAppointment(AppointmentSchedulerRequest request) {
        Optional<Appointment> appointmentByDoctorIdOrPatienceId = repository.findAppointmentByDoctorIdOrPatienceId(request.doctorId(), request.patienceId());
        if (appointmentByDoctorIdOrPatienceId.isPresent()) {
            log.warn("Appointment with id {} already exists in the DB", appointmentByDoctorIdOrPatienceId.get().getId());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Appointment appointment = AppointmentMapping.INSTANCE.mapToEntity(request);
        appointment.setStatus(Status.SCHEDULE);

        repository.save(appointment);
        log.info("Appointment was saved successfully for patienceId {} and doctorId {}", appointment.getPatienceId(), appointment.getDoctorId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
