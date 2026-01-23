package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public class CargoDelivery extends Delivery {
    public CargoDelivery(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void deliverPackage(String packageId) {
        System.out.println("Package " + packageId + " delivered by cargo transport.");
    }
}
