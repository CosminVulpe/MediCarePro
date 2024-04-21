package com.medicarepro.patiencemanagement.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class InsuranceInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String insuranceProvider;
    private Long insurancePolicyNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate coverageDateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate coverageDateEnd;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Patience patience;
}
