package com.api.drone.repository.entity;

import com.api.drone.model.dto.MedicationDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "MEDICATION")
public class MedicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "WEIGHT")
    private Integer weight;

    @Column(name = "CODE")
    private String code;

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private DroneEntity drone;

    public static MedicationEntity from(MedicationDto medicationDto) {
        MedicationEntity medicationEntity = new MedicationEntity();
        medicationEntity.setCode(medicationDto.getCode());
        medicationEntity.setDrone(
                medicationDto.getDroneDto() == null ?
                        new DroneEntity() :
                        DroneEntity.from(medicationDto.getDroneDto())
        );
        medicationEntity.setName(medicationDto.getName());
        medicationEntity.setWeight(medicationDto.getWeight());
        medicationEntity.setImagePath(medicationDto.getImagePath());
        return medicationEntity;
    }
}
