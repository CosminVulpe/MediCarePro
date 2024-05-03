package com.medicarepro.patiencemanagement.service.repository;

import com.medicarepro.patiencemanagement.service.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
}
