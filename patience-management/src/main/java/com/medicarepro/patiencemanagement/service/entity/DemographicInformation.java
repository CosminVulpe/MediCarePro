package com.medicarepro.patiencemanagement.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.medicarepro.patiencemanagement.service.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DemographicInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeOfBirth;
    private boolean married;
    private boolean hasKids;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Patience patience;
}
