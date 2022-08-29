package com.api.drone.controller;

import com.api.drone.model.dto.MedicationDto;
import com.api.drone.model.enums.Model;
import com.api.drone.model.enums.State;
import com.api.drone.repository.DroneRepository;
import com.api.drone.repository.entity.DroneEntity;
import com.api.drone.repository.entity.MedicationEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@RunWith(SpringRunner.class)
@WebMvcTest(DroneControlController.class)*/
@SpringBootTest
@AutoConfigureMockMvc
class DroneControlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneRepository droneRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void droneRegistration() throws Exception {
        mockMvc.perform(put("/api/drone/control/register?serial=ADFAF1&model=Middleweight&battery=100&state=IDLE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void loadMedication() throws Exception {
        DroneEntity droneEntity = new DroneEntity();
        droneEntity.setId(1L);
        droneEntity.setState(State.LOADED);
        droneEntity.setMedicationEntityList(Collections.singletonList(new MedicationEntity()));
        droneEntity.setBatteryCapacity(100);
        droneEntity.setModel(Model.Lightweight);
        droneEntity.setSerial("ADFAF1");
        when(droneRepository.findBySerial(anyString())).thenReturn(droneEntity);
        mockMvc.perform(put("/api/drone/control/medicationLoad?serial=ADFAF1&name=aspirine&weight=100&code=ADFAF1&imagePath=path"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void loadMedicationsByNames() throws Exception {
        mockMvc.perform(put("/api/drone/control/medicationByNamesLoad?serial=ADFAF1&medicationsList=[]"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void loadMedications() throws Exception {
        List<MedicationDto> medicationDtos = Collections.singletonList(MedicationDto.builder().build());
            mockMvc.perform(put("/api/drone/control/medicationsLoad/{serial}", "ADSFA124")
                            .content(mapper.writeValueAsString(medicationDtos))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
    }

    @Test
    void getDroneMedications() throws Exception {
        mockMvc.perform(get("/api/drone/control/checkMedications?serial=ADFAF1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getAvailableDronees() throws Exception {
        mockMvc.perform(get("/api/drone/control/checkAvailable"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getDroneBatteryLevel() throws Exception {
        mockMvc.perform(get("/api/drone/control/checkBattery?serial=ADFAF1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}