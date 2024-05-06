package com.medicarepro.patiencemanagement.service.repository;

import com.medicarepro.patiencemanagement.service.entity.Patience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatienceRepository extends JpaRepository<Patience, Long> {

    @Query(value = """
            SELECT COUNT(SPLIT_PART(name, ' ', 2))
             FROM demographic_information 
             WHERE upper(SPLIT_PART(name, ' ', 2)) = upper(:name)
            """, nativeQuery = true)
    int doesPatienceExist(String name);

}
