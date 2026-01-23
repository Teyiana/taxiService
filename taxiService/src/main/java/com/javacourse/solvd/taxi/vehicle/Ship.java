package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public class Ship extends CargoTransport {

    public Ship(String name, String phoneNumber, double loadCapacity, Position currentPosition, CargoType cargoType) {
        super(name, phoneNumber, loadCapacity, currentPosition, cargoType);
    }

    @Override
    protected void loadCargo(double weight) {
        System.out.println("Loading " + weight + " tons of cargo onto the ship.");

    }
}
