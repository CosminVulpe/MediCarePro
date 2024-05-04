package com.healthcaremanagement.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DoctorSpecialty specialty;

    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private ContactInfo contactInfo;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilityList;

    @ElementCollection
    private List<Long> patienceIds;
}
