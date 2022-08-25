package com.api.drone.util.logging.mapping;

import lombok.experimental.UtilityClass;
import org.springframework.data.util.Pair;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class LogControlMap {

    public static final String ERROR_REGISTER = "ERROR_REGISTER";
    public static final String INFO_REGISTER = "INFO_REGISTER";
    public static final String DRONE_ABSENT = "DRONE_ABSENT";
    public static final String ERROR_LOADED = "ERROR_LOADED";
    public static final String INFO_LOADED = "INFO_LOADED";
    public static final String ERROR_LOADED_BY_NAMES = "ERROR_LOADED_BY_NAMES";
    public static final String INFO_LOADED_BY_NAMES = "INFO_LOADED_BY_NAMES";
    public static final String INFO_LOADING = "INFO_LOADING";
    public static final String INFO_GET_LOADED_MEDICATIONS = "INFO_GET_LOADED_MEDICATIONS";
    public static final String ERROR_GET_LOADED_MEDICATIONS = "ERROR_GET_LOADED_MEDICATIONS";


    private final Map<String, String> logsControlMap = Stream.of(
            Pair.of(ERROR_REGISTER, "Error with drone regisering: drone = {}"),
            Pair.of(INFO_REGISTER, "Success with drone regisering: drone = {}"),
            Pair.of(DRONE_ABSENT, "Can't find drone with serial: {}"),
            Pair.of(ERROR_LOADED, "Error with drone loading: drone = {}, Medication = {}"),
            Pair.of(INFO_LOADED, "Success with drone loading: drone = {}, Medication = {}"),
            Pair.of(ERROR_LOADED_BY_NAMES, "Error with drone loading by names: drone = {}, Medications = {}"),
            Pair.of(INFO_LOADED_BY_NAMES, "Success with drone loading: drone = {}, Medications = {}"),
            Pair.of(INFO_LOADING, "Loading drone: drone = {}"),
            Pair.of(INFO_GET_LOADED_MEDICATIONS, "Loaded medications: {}"),
            Pair.of(ERROR_GET_LOADED_MEDICATIONS, "Error with loading medications")
    ).collect(Collectors.toMap(
                    Pair::getFirst,
                    Pair::getSecond
            )
    );

    public static String getLogMsg(String key) {
        return logsControlMap.get(key);
    }
}
