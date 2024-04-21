package com.medicarepro.patiencemanagement.service.repository;

import com.medicarepro.patiencemanagement.service.entity.ContractInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractInformationRepository extends JpaRepository<ContractInformation, Long> {
}
