package com.medicarepro.patiencemanagement.service.repository;

import com.medicarepro.patiencemanagement.service.entity.DemographicInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemographicInformationRepository extends JpaRepository<DemographicInformation, Long> {
}
