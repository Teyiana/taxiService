package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public class CourierDelivery extends Delivery {

    public CourierDelivery(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void deliverPackage(String packageId) {
        System.out.println("Delivering package " + packageId +
                " by " + getName());
    }
}
