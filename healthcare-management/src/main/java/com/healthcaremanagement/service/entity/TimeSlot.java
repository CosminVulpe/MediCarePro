package com.healthcaremanagement.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime startTime;
    private LocalTime endTime;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Availability availability;
}
