package com.api.drone.model.dto;

import com.api.drone.repository.entity.MedicationEntity;
import lombok.Builder;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Data
@Builder
public class MedicationDto {

    private String name;

    private Integer weight;

    private String code;

    private String imagePath;

    private BufferedImage image;

    private DroneDto DroneDto;

    public static MedicationDto from(MedicationEntity medicationEntity) {
        BufferedImage bufferedImage = new BufferedImage(1, 1, Image.SCALE_DEFAULT);
        try {
            bufferedImage = ImageIO.read(new File(medicationEntity.getImagePath()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return MedicationDto.builder()
                .code(medicationEntity.getCode())
                .image(bufferedImage)
                .imagePath(medicationEntity.getImagePath())
                .name(medicationEntity.getName())
                .weight(medicationEntity.getWeight())
                .build();
    }
}
