package com.appointmentscheduling.service.repository;

import com.appointmentscheduling.service.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAppointmentByDoctorIdOrPatienceId(Long doctorId, Long patienceId);
}
