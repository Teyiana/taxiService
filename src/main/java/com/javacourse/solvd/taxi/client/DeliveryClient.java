package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeliveryClient extends Client {

    private static final Logger LOGGER = LogManager.getLogger(DeliveryClient.class);

    @Getter
    private final double packageWeight;

    public DeliveryClient(String name, String phoneNumber, Position currentPosition, double packageWeight) {
        super(name, phoneNumber, currentPosition);
        this.packageWeight = packageWeight;
    }

    @Override
    public void createOrder() {

        LOGGER.info("Delivery client {} created a delivery order. Package weight: {} kg", getName(), packageWeight);
    }
}
