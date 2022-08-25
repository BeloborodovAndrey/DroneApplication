package com.api.drone.controller;

import com.api.drone.model.dto.DroneDto;
import com.api.drone.model.dto.MedicationDto;
import com.api.drone.model.enums.Model;
import com.api.drone.model.enums.State;
import com.api.drone.service.DroneControlService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/drone/control")
@AllArgsConstructor
public class DroneControlController {

    private DroneControlService droneControlService;

    @PutMapping("/register")
    public ResponseEntity<Boolean> DroneRegistration(
            @RequestParam(name = "serial") String serial,
            @RequestParam(name = "model") String model,
            @RequestParam(name = "battery") Integer battery,
            @RequestParam(name = "state") String state
    ) {
        DroneDto droneDto = DroneDto.builder()
                .serial(serial)
                .model(Model.valueOf(model))
                .batteryCapacity(battery)
                .state(State.valueOf(state))
                .build();
        return new ResponseEntity<>(droneControlService.register(droneDto), HttpStatus.OK);
    }

    @PutMapping("/medicationLoad")
    public ResponseEntity<Boolean> loadMedication(
            @RequestParam(name = "serial") String serial,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "weight") Integer weight,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "imagePath") String imagePath
    ) {
        MedicationDto medicationDto = MedicationDto.builder()
                .name(name)
                .weight(weight)
                .code(code)
                .imagePath(imagePath)
                .build();
        return new ResponseEntity<>(droneControlService.loadMedication(serial, medicationDto), HttpStatus.OK);
    }

    @PutMapping("/medicationByNamesLoad")
    public ResponseEntity<Boolean> loadMedicationsByNames(
            @RequestParam(name = "serial") String serial,
            @RequestParam(name = "medicationsList") List<String> medicationsList
    ) {
        return new ResponseEntity<>(droneControlService.loadWithMedicationsByNames(serial, medicationsList), HttpStatus.OK);
    }

    @PutMapping("/medicationsLoad")
    public ResponseEntity<Boolean> loadMedications(
            @RequestParam(name = "serial") String serial,
            @RequestParam(name = "medicationsList") List<MedicationDto> medicationsList
    ) {
        return new ResponseEntity<>(droneControlService.load(serial, medicationsList), HttpStatus.OK);
    }

    @GetMapping("/checkMedications")
    public ResponseEntity<List<MedicationDto>> getDroneMedications(
            @RequestParam(name = "serial") String serial
    ) {
        return new ResponseEntity<>(droneControlService.getLoadedMedications(serial), HttpStatus.OK);
    }

    @GetMapping("/checkAvailable")
    public ResponseEntity<List<DroneDto>> getAvailableDronees() {
        return new ResponseEntity<>(droneControlService.checkAvailableDrones(), HttpStatus.OK);
    }

    @GetMapping("/checkBattery")
    public ResponseEntity<Integer> getDroneBatteryLevel(
            @RequestParam(name = "serial") String serial
    ) {
        return new ResponseEntity<>(droneControlService.checkDroneBattery(serial), HttpStatus.OK);
    }
}
