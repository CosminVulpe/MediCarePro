package com.appointmentscheduling.service.entity;

import com.appointmentscheduling.service.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patienceId;

    private Long doctorId;

    private DayOfWeek dayOfWeek;

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String notes;
}

