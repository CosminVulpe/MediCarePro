package com.medicarepro.patiencemanagement.service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Patience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patienceId;

    @JsonManagedReference
    @OneToOne(mappedBy = "patience", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DemographicInformation demographicInformation;

    @JsonManagedReference
    @OneToOne(mappedBy = "patience", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContractInformation contractInformation;

    @JsonManagedReference
    @OneToOne(mappedBy = "patience", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private InsuranceInformation insuranceInformation;

    @JsonManagedReference
    @OneToOne(mappedBy = "patience", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MedicalHistory medicalHistory;

    private Long doctorId;
}
