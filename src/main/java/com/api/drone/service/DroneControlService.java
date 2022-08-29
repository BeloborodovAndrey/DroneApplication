package com.api.drone.service;

import com.api.drone.model.dto.DroneDto;
import com.api.drone.model.dto.MedicationDto;
import com.api.drone.model.enums.State;
import com.api.drone.repository.DroneRepository;
import com.api.drone.repository.MedicationRepository;
import com.api.drone.repository.entity.DroneEntity;
import com.api.drone.repository.entity.MedicationEntity;
import com.api.drone.util.DroneCheckConditions;
import com.api.drone.util.logging.DroneLogger;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main Drones control class
 */
@Service
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class DroneControlService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public boolean register(DroneDto droneDto) {
        try {
            if (droneDto.getSerial() == null) {
                return false;
            }
            if (Optional.ofNullable(droneRepository.findBySerial(droneDto.getSerial())).isPresent()) {
                return false;
            }
            DroneEntity droneEntity = DroneEntity.from(droneDto);
            droneEntity.setState(State.IDLE);
            droneRepository.save(droneEntity);
            DroneLogger.droneRegisterInfoLog(droneDto);
            return true;
        } catch (Exception ex) {
            DroneLogger.droneRegisterErrorLog(droneDto, ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean loadMedication(String serial, MedicationDto medicationDto) {
        DroneEntity drone = droneRepository.findBySerial(serial);
        if (isAbsentingDrone(serial, drone)) return false;
        DroneDto droneDto = DroneDto.from(drone);
        try {
            if (DroneCheckConditions.checkBattery(droneDto) && DroneCheckConditions.checkWeight(droneDto)) {
                drone.setState(State.LOADING);
                DroneLogger.droneLoadingInfoLog(droneDto);
                droneRepository.save(drone);
                drone.setMedicationEntityList(Collections.singletonList(MedicationEntity.from(medicationDto)));
                droneRepository.save(drone);
                drone.setState(State.LOADED);
                DroneLogger.droneLoadedInfoLog(droneDto, medicationDto);
                return true;
            }
            return false;
        } catch (Exception ex) {
            DroneLogger.droneLoadedErrorLog(droneDto, medicationDto, ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean loadWithMedicationsByNames(String serial, List<String> medicationNameList) {
        DroneEntity drone = droneRepository.findBySerial(serial);
        if (isAbsentingDrone(serial, drone)) return false;
        DroneDto droneDto = DroneDto.from(drone);
        try {
            if (DroneCheckConditions.checkBattery(droneDto) && DroneCheckConditions.checkWeight(droneDto)) {
                drone.setState(State.LOADING);
                DroneLogger.droneLoadingInfoLog(droneDto);
                droneRepository.save(drone);
                List<MedicationEntity> medicationEntityList = medicationRepository.findAllByNameIn(medicationNameList);
                drone.setMedicationEntityList(medicationEntityList);
                droneRepository.save(drone);
                drone.setState(State.LOADED);
                DroneLogger.droneLoadedItemsInfoLog(droneDto, medicationNameList);
                return true;
            }
            return false;
        } catch (Exception ex) {
            DroneLogger.droneLoadedItemsErrorLog(droneDto, medicationNameList, ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean load(String serial, List<MedicationDto> medicationList) {
        DroneEntity drone = droneRepository.findBySerial(serial);
        if (isAbsentingDrone(serial, drone)) return false;
        drone.setMedicationEntityList(
                medicationList.stream()
                        .map(MedicationEntity::from)
                        .collect(Collectors.toList())
        );
        DroneDto droneDto = DroneDto.from(drone);
        try {
            if (DroneCheckConditions.checkBattery(droneDto) && DroneCheckConditions.checkWeight(droneDto)) {
                DroneLogger.droneLoadingInfoLog(droneDto);
                drone.setState(State.LOADING);
                droneRepository.save(drone);
                drone.setMedicationEntityList(medicationList.stream()
                        .map(MedicationEntity::from)
                        .collect(Collectors.toList())
                );
                droneRepository.save(drone);
                DroneLogger.droneLoadedItemsInfoLog(droneDto, medicationList);
                drone.setState(State.LOADED);
                return true;
            }
            return false;
        } catch (Exception ex) {
            DroneLogger.droneLoadedItemsErrorLog(droneDto, medicationList, ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public List<MedicationDto> getLoadedMedications(String serial) {
        try {
            DroneEntity droneEntity = droneRepository.findBySerial(serial);
            if (!Optional.ofNullable(droneEntity).isPresent()) {
                DroneLogger.droneAbsent(serial);
                return new ArrayList<>();
            }
            List<MedicationDto> medicationDtoList = droneEntity.getMedicationEntityList().stream()
                    .map(MedicationDto::from)
                    .collect(Collectors.toList());
            DroneLogger.loadedItemsInfoLog(medicationDtoList);
            return medicationDtoList;
        } catch (Exception ex) {
            DroneLogger.loadedItemsErrorLog();
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<DroneDto> checkAvailableDrones() {
        try {
            List<DroneEntity> DroneEntities = droneRepository.findAll();
            if (!DroneEntities.isEmpty()) {
                return DroneEntities.stream()
                        .map(DroneDto::from)
                        .filter(DroneCheckConditions::checkBattery)
                        .filter(DroneCheckConditions::checkWeight)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Integer checkDroneBattery(String serial) {
        try {
            DroneEntity droneEntity = droneRepository.findBySerial(serial);
            if (Optional.ofNullable(droneEntity).isPresent()) {
                return droneEntity.getBatteryCapacity();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    private boolean isAbsentingDrone(String serial, DroneEntity drone) {
        if (!Optional.ofNullable(drone).isPresent()) {
            DroneLogger.droneAbsent(serial);
            return true;
        }
        return false;
    }
}
