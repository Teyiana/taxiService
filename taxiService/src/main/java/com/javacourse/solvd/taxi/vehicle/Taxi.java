package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public abstract class Taxi extends Vehicle implements FareCalculable {

    private double rating = 5.0;


    public Taxi(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    public double getRating() {
        return rating;
    }

    @Override
    public void startTrip() {
        if (!isBusy()) {
            setBusy(true);
            System.out.println("Taxi " + getName() + " started the trip");
        } else {
            System.out.println("Taxi is already busy");
        }
    }

    @Override
    public double calculateFare(double distance, int duration) {
        double baseFare = 3.0;
        double costPerKm = 1.5;
        double costPerMinute = 0.5;

        double fare = baseFare + (costPerKm * distance) + (costPerMinute * duration);
        return fare;
    }

    protected abstract void navigateTo(Position destination);
}
