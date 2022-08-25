package com.api.drone.util.logging;

import com.api.drone.model.dto.DroneDto;
import com.api.drone.model.dto.MedicationDto;
import com.api.drone.repository.entity.DroneEntity;
import com.api.drone.util.logging.mapping.LogControlMap;
import com.api.drone.util.logging.mapping.LogStateMap;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.api.drone.util.logging.mapping.LogControlMap.*;
import static com.api.drone.util.logging.mapping.LogStateMap.*;

@UtilityClass
@Slf4j
public class DroneLogger {

    public static void droneRegisterErrorLog(DroneDto droneDto, String errMsg) {
        log.error(LogControlMap.getLogMsg(ERROR_REGISTER) + errMsg, droneDto);
    }

    public static void droneRegisterInfoLog(DroneDto droneDto) {
        log.info(LogControlMap.getLogMsg(INFO_REGISTER), droneDto);
    }

    public static void droneAbsent(String serial) {
        log.debug(LogControlMap.getLogMsg(DRONE_ABSENT), serial);
    }

    public static void droneLoadingInfoLog(DroneDto droneDto) {
        log.info(LogControlMap.getLogMsg(INFO_LOADING), droneDto);
    }

    public static void droneLoadedErrorLog(DroneDto droneDto, MedicationDto medicationDto, String errMsg) {
        log.error(LogControlMap.getLogMsg(ERROR_LOADED) + errMsg, droneDto, medicationDto);
    }

    public static void droneLoadedInfoLog(DroneDto droneDto, MedicationDto medicationDto) {
        log.info(LogControlMap.getLogMsg(INFO_LOADED), droneDto, medicationDto);
    }

    public static <T> void droneLoadedItemsErrorLog(DroneDto droneDto, List<T> medications, String errMsg) {
        log.error(LogControlMap.getLogMsg(ERROR_LOADED_BY_NAMES) + errMsg, droneDto, medications);
    }

    public static <T> void droneLoadedItemsInfoLog(DroneDto droneDto, List<T> medications) {
        log.info(LogControlMap.getLogMsg(INFO_LOADED_BY_NAMES), droneDto, medications);
    }

    public static <T> void loadedItemsInfoLog(List<T> medications) {
        log.info(LogControlMap.getLogMsg(INFO_GET_LOADED_MEDICATIONS), medications);
    }

    public static void loadedItemsErrorLog() {
        log.info(LogControlMap.getLogMsg(ERROR_GET_LOADED_MEDICATIONS));
    }

    public static class DroneStateLogger {

        public static void batteryCapacity(DroneEntity droneEntity, int capacity) {
            log.info(LogStateMap.getLogMsg(CAPACITY), droneEntity, capacity);
        }

        public static void droneStateIdle(DroneEntity droneEntity) {
            log.info(LogStateMap.getLogMsg(IDLE_STATE), droneEntity);
        }

        public static void droneStateDelivering(DroneEntity droneEntity) {
            log.info(LogStateMap.getLogMsg(DELIVERING_STATE), droneEntity);
        }

        public static void droneStateDelivered(DroneEntity droneEntity) {
            log.info(LogStateMap.getLogMsg(DELIVERED_STATE), droneEntity);
        }

        public static void droneStateReturning(DroneEntity droneEntity) {
            log.info(LogStateMap.getLogMsg(RETURNING_STATE), droneEntity);
        }
    }

}
