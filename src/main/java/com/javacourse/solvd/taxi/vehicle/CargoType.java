package com.javacourse.solvd.taxi.vehicle;

import lombok.Getter;

public enum CargoType {
    FOOD("Food"), FURNITURE("Furniture"), BUILDING_MATERIALS("Building Materials"), OTHER("Other");

    @Getter
    private final String name;

    CargoType(String name) {
        this.name = name;
    }

}
