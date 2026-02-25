package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Taxi extends Vehicle implements FareCalculable {

    private static final Logger LOGGER = LogManager.getLogger(Taxi.class);

    @Getter
    private final double rating = 5.0;


    public Taxi(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void startTrip() {
        if (!isBusy()) {
            setBusy(true);
            LOGGER.info("Taxi {} started the trip", getName());
        } else {
            LOGGER.info("Taxi is already busy");
        }
    }

    @Override
    public double calculateFare(double distance, int duration) {
        double baseFare = 3.0;
        double costPerKm = 1.5;
        double costPerMinute = 0.5;

        double fare = baseFare + (costPerKm * distance) + (costPerMinute * duration);
        return fare;
    }

    protected abstract void navigateTo(Position destination);
}
