package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CourierDelivery extends Delivery {

    private static final Logger LOGGER = LogManager.getLogger(CourierDelivery.class);

    public CourierDelivery(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void deliverPackage(String packageId) {
        LOGGER.info("Delivering package {} by {}",packageId, getName());
    }

    @Override
    public void updatePosition(double latitude, double longitude) {
        super.updatePosition(latitude, longitude);
    }
}
