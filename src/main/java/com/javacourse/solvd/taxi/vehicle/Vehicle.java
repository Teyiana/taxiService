package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
public abstract class Vehicle implements GPSNavigable {

    private static final Logger LOGGER = LogManager.getLogger(Vehicle.class);


    private final String name;
    private final String phoneNumber;
    Position currentPosition;
    private boolean busy = false;

    public Vehicle(String name, String phoneNumber, Position currentPosition) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentPosition = currentPosition;
    }

    public abstract void startTrip();

    @Override
    public void navigateTo(double latitude, double longitude) {
        LOGGER.info("Navigating to coordinates: ({}, {})", latitude, longitude);
        currentPosition = new Position(latitude, longitude);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", currentPosition=" + currentPosition +
                ", busy=" + busy +
                '}';
    }
}
