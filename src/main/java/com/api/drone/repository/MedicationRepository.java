package com.api.drone.repository;

import com.api.drone.repository.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
    List<MedicationEntity> findAllByNameIn(List<String> names);
}
