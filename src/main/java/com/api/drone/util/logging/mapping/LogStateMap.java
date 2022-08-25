package com.api.drone.util.logging.mapping;

import lombok.experimental.UtilityClass;
import org.springframework.data.util.Pair;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class LogStateMap {

    public static final String IDLE_STATE = "IDLE_STATE";
    public static final String LOADING_STATE = "LOADING";
    public static final String LOADED_STATE = "LOADED";
    public static final String DELIVERING_STATE = "DELIVERING";
    public static final String DELIVERED_STATE = "DELIVERED";
    public static final String RETURNING_STATE = "RETURNING";
    public static final String CAPACITY = "CAPACITY";

    private final Map<String, String> logsStateMap = Stream.of(
            Pair.of(IDLE_STATE, "Drone now in Idle state: drone = {}"),
            Pair.of(LOADING_STATE, "Drone now in loading state: drone = {}"),
            Pair.of(LOADED_STATE, "Drone now in loaded state: drone = {}"),
            Pair.of(DELIVERING_STATE, "Drone now in delivering state: drone = {}"),
            Pair.of(DELIVERED_STATE, "Drone now in delivered state: drone = {}"),
            Pair.of(RETURNING_STATE, "Drone now in returning state: drone = {}"),
            Pair.of(CAPACITY, "Drone battery capacity now: drone = {}, capacity= {}")
    ).collect(Collectors.toMap(
                    Pair::getFirst,
                    Pair::getSecond
            )
    );

    public static String getLogMsg(String key) {
        return logsStateMap.get(key);
    }
}
