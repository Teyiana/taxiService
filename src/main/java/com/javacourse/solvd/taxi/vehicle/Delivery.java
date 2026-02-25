package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Delivery extends Vehicle implements Trackable {

    private static final Logger LOGGER = LogManager.getLogger(Delivery.class);

    public Delivery(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void startTrip() {
        if (!isBusy()) {
            setBusy(true);
            LOGGER.info("Delivery started by {}", getName());
        } else {
            LOGGER.info("{} is already on a delivery.", getName());
        }
    }

    public abstract void deliverPackage(String packageId);

    @Override
    public void updatePosition(double latitude, double longitude) {
        currentPosition.longitude();
        currentPosition.latitude();
        LOGGER.info("Updated position of {} to {}, {}", getName(), latitude, longitude);
    }
}
