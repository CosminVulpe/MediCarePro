package com.medicarepro.patiencemanagement.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.medicarepro.patiencemanagement.service.converter.ConverterList;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = ConverterList.class)
    @Column(name = "conditions", nullable = false)
    private List<String> conditions = new ArrayList<>();

    @Convert(converter = ConverterList.class)
    @Column(name = "allergies", nullable = false)
    private List<String> allergies = new ArrayList<>();

    @Convert(converter = ConverterList.class)
    @Column(name = "medications", nullable = false)
    private List<String> medications = new ArrayList<>();

    @Convert(converter = ConverterList.class)
    @Column(name = "surgeries", nullable = false)
    private List<String> surgeries = new ArrayList<>();

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Patience patience;

}
