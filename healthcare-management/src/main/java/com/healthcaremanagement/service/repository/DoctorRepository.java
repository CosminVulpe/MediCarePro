package com.healthcaremanagement.service.repository;

import com.healthcaremanagement.service.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = """
            SELECT * FROM doctor WHERE name LIKE '%' || :name || '%'
            """, nativeQuery = true)
    List<Doctor> findDoctorsByName(String name);
}
