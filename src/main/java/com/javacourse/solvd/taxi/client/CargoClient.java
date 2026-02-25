package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CargoClient extends Client {

    private static final Logger LOGGER = LogManager.getLogger(CargoClient.class);

    public CargoClient(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

 @Override
    public void createOrder() {
        LOGGER.info("Cargo client {} created a cargo transport order.", getName());
    }
}
