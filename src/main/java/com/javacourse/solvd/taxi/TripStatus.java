package com.javacourse.solvd.taxi;


import lombok.Getter;

public enum TripStatus {

    NEW("New order"), WAITING_FOR_DRIVER("Waiting for driver"), IN_PROGRESS("In progress"), COMPLETED("Completed"), CANCELLED("Cancelled");

    @Getter
    private final String status;

    TripStatus(String status) {
        this.status = status;
    }
}
