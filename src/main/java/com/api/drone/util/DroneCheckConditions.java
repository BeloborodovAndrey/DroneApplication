package com.api.drone.util;

import com.api.drone.model.dto.DroneDto;
import com.api.drone.model.dto.MedicationDto;
import com.api.drone.model.enums.Model;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DroneCheckConditions {
    private static final Integer BATTERY_CAPACITY_LIMIT = 25;

    public static boolean checkWeight(DroneDto droneDto) {
        Integer sumWeight = droneDto.getMedicationDtoList().stream()
                .mapToInt(MedicationDto::getWeight)
                .sum();
        return Model.getWeight(droneDto.getModel()).compareTo(sumWeight) >= 0;
    }

    public static boolean checkBattery(DroneDto droneDto) {
        return droneDto.getBatteryCapacity().compareTo(BATTERY_CAPACITY_LIMIT) > 0;
    }
}
