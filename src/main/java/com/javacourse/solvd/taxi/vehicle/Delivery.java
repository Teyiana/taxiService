package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public abstract class Delivery extends Vehicle implements Trackable {
    public Delivery(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void startTrip() {
        if (!isBusy()) {
            setBusy(true);
            System.out.println("Delivery started by " + getName());
        } else {
            System.out.println(getName() + " is already on a delivery.");
        }
    }

    public abstract void deliverPackage(String packageId);

    @Override
    public void updatePosition(double latitude, double longitude) {
        currentPosition.longitude();
        currentPosition.latitude();
        System.out.println("Updated position of " + getName() + " to: " + latitude + ", " + longitude);
    }
}
