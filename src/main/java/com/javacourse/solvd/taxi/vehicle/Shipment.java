package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Shipment extends CargoTransport {

    private static final Logger LOGGER = LogManager.getLogger(Shipment.class);

    public Shipment(String name, String phoneNumber, double loadCapacity, Position currentPosition, CargoType cargoType) {
        super(name, phoneNumber, loadCapacity, currentPosition, cargoType);
    }

    @Override
    protected void loadCargo(double weight) {
        LOGGER.info("Loading {} tons of cargo onto the ship.", weight);

    }
}
