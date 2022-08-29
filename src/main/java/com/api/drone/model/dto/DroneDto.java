package com.api.drone.model.dto;

import com.api.drone.model.enums.Model;
import com.api.drone.model.enums.State;
import com.api.drone.repository.entity.DroneEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DroneDto {

    private String serial;

    private Model model;

    private Integer batteryCapacity;

    private State state;

    private List<MedicationDto> medicationDtoList;

    public static DroneDto from(DroneEntity droneEntity) {
        return DroneDto.builder()
                .state(droneEntity.getState())
                .batteryCapacity(droneEntity.getBatteryCapacity())
                .model(droneEntity.getModel())
                .serial(droneEntity.getSerial())
                .medicationDtoList(
                        droneEntity.getMedicationEntityList().stream()
                                .map(MedicationDto::from)
                                .collect(Collectors.toList())
                ).build();
    }
}
