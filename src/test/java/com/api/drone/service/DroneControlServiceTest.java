package com.api.drone.service;

import com.api.drone.model.dto.DroneDto;
import com.api.drone.model.dto.MedicationDto;
import com.api.drone.model.enums.Model;
import com.api.drone.model.enums.State;
import com.api.drone.repository.DroneRepository;
import com.api.drone.repository.entity.DroneEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class DroneControlServiceTest {

    @Autowired
    private DroneControlService droneControlService;

    @MockBean
    private DroneRepository droneRepository;

    private static final String SERIAL = "DAFFAF";

    @Test
    void register() {
        DroneDto droneDto = getDroneDto();
        assertTrue(droneControlService.register(droneDto));
    }

    @Test
    void loadMedication() {
        DroneDto droneDto = getDroneDto();
        MedicationDto medicationDto = getMedicationDto();
        DroneEntity droneEntity = DroneEntity.from(droneDto);
        when(droneRepository.findBySerial(anyString())).thenReturn(droneEntity);

        assertTrue(droneControlService.loadMedication(droneDto.getSerial(), medicationDto));
    }

    @Test
    void loadWithMedicationsByNames() {
        DroneDto droneDto = getDroneDto();
        MedicationDto medicationDto = getMedicationDto();
        DroneEntity droneEntity = DroneEntity.from(droneDto);
        when(droneRepository.findBySerial(anyString())).thenReturn(droneEntity);

        assertTrue(droneControlService.loadWithMedicationsByNames(droneDto.getSerial(), Collections.singletonList(medicationDto.getName())));
    }

    @Test
    void load() {
        DroneDto droneDto = getDroneDto();
        MedicationDto medicationDto = getMedicationDto();
        DroneEntity droneEntity = DroneEntity.from(droneDto);
        when(droneRepository.findBySerial(anyString())).thenReturn(droneEntity);

        assertTrue(droneControlService.load(droneDto.getSerial(), Collections.singletonList(medicationDto)));
    }

    @Test
    void getLoadedMedications() {
        DroneDto droneDto = getDroneDto();
        DroneEntity droneEntity = DroneEntity.from(droneDto);
        when(droneRepository.findBySerial(anyString())).thenReturn(droneEntity);

        assertNotNull(droneControlService.getLoadedMedications(droneDto.getSerial()));
    }

    @Test
    void checkAvailableDrones() {
        DroneDto droneDto = getDroneDto();
        DroneEntity droneEntity = DroneEntity.from(droneDto);
        when(droneRepository.findAll()).thenReturn(Collections.singletonList(droneEntity));

        assertNotNull(droneControlService.checkAvailableDrones());
    }

    @Test
    void checkDroneBattery() {
        DroneDto droneDto = getDroneDto();
        DroneEntity droneEntity = DroneEntity.from(droneDto);
        when(droneRepository.findBySerial(anyString())).thenReturn(droneEntity);

        assertNotNull(droneControlService.checkDroneBattery(droneDto.getSerial()));
    }

    private DroneDto getDroneDto() {
        return DroneDto.builder()
                .state(State.IDLE)
                .serial(SERIAL)
                .medicationDtoList(
                        Collections.singletonList(getMedicationDto())
                )
                .batteryCapacity(100)
                .model(Model.Lightweight)
                .build();
    }

    private MedicationDto getMedicationDto() {
        return MedicationDto.builder()
                .imagePath("")
                .code("code")
                .name("name")
                .weight(12)
                .image(new BufferedImage(1, 1, Image.SCALE_DEFAULT))
                .build();
    }
}