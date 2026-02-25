package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BusinessClass extends Taxi {

    private static final Logger LOGGER = LogManager.getLogger(BusinessClass.class);

    public BusinessClass(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    protected void navigateTo(Position destination) {
        LOGGER.info("Taxi {} navigating to [{}, {}]", getName(), destination.latitude(), destination.longitude() );
    }
}
