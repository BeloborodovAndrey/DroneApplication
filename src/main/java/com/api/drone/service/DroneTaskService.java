package com.api.drone.service;

import com.api.drone.model.enums.State;
import com.api.drone.repository.DroneRepository;
import com.api.drone.repository.entity.DroneEntity;
import com.api.drone.util.logging.DroneLogger;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableAsync
@AllArgsConstructor
@Component
public class DroneTaskService {

    private final DroneRepository droneRepository;

    @Async
    @Scheduled(fixedRate = 3000, initialDelay = 10000)
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void scheduleDroneCheckLoadedTask() {
        try {
            List<DroneEntity> droneEntityList = droneRepository.findAll();
            droneEntityList.stream()
                    .filter(droneEntity -> droneEntity.getState().equals(State.LOADED))
                    .forEach(droneEntity -> {
                        droneEntity.setState(State.DELIVERING);
                        droneRepository.save(droneEntity);
                        DroneLogger.DroneStateLogger.droneStateDelivering(droneEntity);
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Async
    @Scheduled(fixedRate = 1000, initialDelay = 1000)
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void scheduleBatteryTask() {
        try {
            List<DroneEntity> droneEntityList = droneRepository.findAll();
            droneEntityList.stream()
                    .filter(droneEntity -> droneEntity.getState().equals(State.DELIVERING) ||
                            droneEntity.getState().equals(State.RETURNING))
                    .forEach(droneEntity -> {
                        int batteryCapacity = droneEntity.getBatteryCapacity();
                        if (batteryCapacity > 0) {
                            droneEntity.setBatteryCapacity(droneEntity.getBatteryCapacity() - 10);
                        }
                        droneRepository.save(droneEntity);
                        DroneLogger.DroneStateLogger.batteryCapacity(droneEntity, droneEntity.getBatteryCapacity());
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Async
    @Scheduled(fixedRate = 5000, initialDelay = 15000)
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void scheduleDroneCheckDeliveringTask() {
        try {
            List<DroneEntity> droneEntityList = droneRepository.findAll();
            droneEntityList.stream()
                    .filter(droneEntity -> droneEntity.getState().equals(State.DELIVERING))
                    .forEach(droneEntity -> {
                        droneEntity.setState(State.DELIVERED);
                        droneRepository.save(droneEntity);
                        DroneLogger.DroneStateLogger.droneStateDelivered(droneEntity);
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Async
    @Scheduled(fixedRate = 3000, initialDelay = 20000)
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void scheduleDroneCheckDeliveredTask() {
        try {
            List<DroneEntity> droneEntityList = droneRepository.findAll();
            droneEntityList.stream()
                    .filter(droneEntity -> droneEntity.getState().equals(State.DELIVERED))
                    .forEach(droneEntity -> {
                        droneEntity.setState(State.RETURNING);
                        droneRepository.save(droneEntity);
                        DroneLogger.DroneStateLogger.droneStateReturning(droneEntity);
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Async
    @Scheduled(fixedRate = 5000, initialDelay = 25000)
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void scheduleDroneCheckReturningTask() {
        try {
            List<DroneEntity> droneEntityList = droneRepository.findAll();
            droneEntityList.stream()
                    .filter(droneEntity -> droneEntity.getState().equals(State.RETURNING))
                    .forEach(droneEntity -> {
                        droneEntity.setState(State.IDLE);
                        droneEntity.setBatteryCapacity(100);
                        droneRepository.save(droneEntity);
                        DroneLogger.DroneStateLogger.droneStateIdle(droneEntity);
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
