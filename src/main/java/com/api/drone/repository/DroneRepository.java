package com.api.drone.repository;

import com.api.drone.repository.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    DroneEntity findBySerial(String serial);
}
