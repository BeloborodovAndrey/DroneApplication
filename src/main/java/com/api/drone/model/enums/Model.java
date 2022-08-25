package com.api.drone.model.enums;

public enum Model {

    Lightweight(100),

    Middleweight(200),

    Cruiserweight(300),

    Heavyweight(500);

    private Integer weight;

    Model(int weight) {
        this.weight = weight;
    }

    public static Integer getWeight(Model model) {
        return model.weight;
    }
}
