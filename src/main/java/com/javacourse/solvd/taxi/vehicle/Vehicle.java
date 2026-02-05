package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public abstract class Vehicle implements GPSNavigable {
    private String name;
    private String phoneNumber;

    Position currentPosition;
    private boolean busy = false;

    public Vehicle(String name, String phoneNumber, Position currentPosition) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentPosition = currentPosition;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public abstract void startTrip();


    @Override
    public void navigateTo(double latitude, double longitude) {
        System.out.println("Navigating to coordinates: (" + latitude + ", " + longitude + ")");
        currentPosition.setLatitude(latitude);
        currentPosition.setLongitude(longitude);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", currentPosition=" + currentPosition +
                ", busy=" + busy +
                '}';
    }
}
