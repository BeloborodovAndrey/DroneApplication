package com.api.drone.repository.entity;

import com.api.drone.model.dto.DroneDto;
import com.api.drone.model.enums.Model;
import com.api.drone.model.enums.State;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "DRONE")
public class DroneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "SERIAL_NUMBER", unique = true)
    private String serial;

    @Column(name = "MODEL")
    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(name = "BATTERY")
    private int batteryCapacity;

    @Column(name = "CURRENT_STATE")
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicationEntity> medicationEntityList;

    public static DroneEntity from(DroneDto droneDto) {
        DroneEntity droneEntity = new DroneEntity();
        droneEntity.setBatteryCapacity(droneDto.getBatteryCapacity());
        droneEntity.setModel(droneDto.getModel());
        if (droneEntity.getMedicationEntityList() != null) {
            droneEntity.setMedicationEntityList(
                    droneDto.getMedicationDtoList().stream()
                            .map(MedicationEntity::from)
                            .collect(Collectors.toList())
            );
        }
        droneEntity.setSerial(droneDto.getSerial());
        droneEntity.setState(droneDto.getState());
        return droneEntity;
    }
}
