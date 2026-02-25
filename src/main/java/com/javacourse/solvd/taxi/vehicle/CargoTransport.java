package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
public abstract class CargoTransport extends Vehicle {

    private static final Logger LOGGER = LogManager.getLogger(CargoTransport.class);

    private final double loadCapacity;
    private double currentLoad;
    private Position currentPosition;
    private CargoType cargoType;

    public CargoTransport(String name, String phoneNumber, double loadCapacity, Position currentPosition, CargoType cargoType) {
        super(name, phoneNumber, currentPosition);
        this.loadCapacity = loadCapacity;
        this.currentPosition = currentPosition;
        this.cargoType = cargoType;
    }

    @Override
    public void startTrip() {
        if (!isBusy()) {
            setBusy(true);
            LOGGER.info("Cargo transport started with load capacity: {}", loadCapacity);
        } else {
            LOGGER.info("Cargo transport is already busy");
        }
    }

    protected abstract void loadCargo(double weight);
}
