package com.medicarepro.patiencemanagement.service.repository;

import com.medicarepro.patiencemanagement.service.entity.Patience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatienceRepository extends JpaRepository<Patience,Long> {
}
