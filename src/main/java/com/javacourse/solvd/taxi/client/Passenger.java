package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Passenger extends Client {

    private static final Logger LOGGER = LogManager.getLogger(Passenger.class);

    @Getter
    @Setter
    private  double rating = 5.0;

    public Passenger(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void createOrder() {
        LOGGER.info("Passenger {} created a taxi order from position: {}, {}", getName(), getCurrentPosition().longitude(), getCurrentPosition().latitude());

    }

}
