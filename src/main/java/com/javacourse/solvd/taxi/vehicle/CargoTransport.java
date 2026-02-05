package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public abstract class CargoTransport extends Vehicle {

    private double loadCapacity;
    private double currentLoad;
    private Position currentPosition;
    private CargoType cargoType;

    public CargoTransport(String name, String phoneNumber, double loadCapacity, Position currentPosition, CargoType cargoType) {
        super(name, phoneNumber, currentPosition);
        this.loadCapacity = loadCapacity;
        this.currentPosition = currentPosition;
        this.cargoType = cargoType;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    protected void setCurrentLoad(double currentLoad) {
        this.currentLoad = currentLoad;
    }


    @Override
    public void startTrip() {
        if (!isBusy()) {
            setBusy(true);
            System.out.println("Cargo transport started with load capacity: " + loadCapacity);
        } else {
            System.out.println("Cargo transport is already busy");
        }

    }
    protected abstract void loadCargo(double weight);
}
