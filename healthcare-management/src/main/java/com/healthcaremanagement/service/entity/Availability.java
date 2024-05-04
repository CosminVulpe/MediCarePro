package com.healthcaremanagement.service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @OneToOne(mappedBy = "availability", cascade = CascadeType.ALL, orphanRemoval = true)
    private TimeSlot timeSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;
}
