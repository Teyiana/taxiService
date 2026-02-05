package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public class EconomyClass extends Taxi {
    public EconomyClass(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    protected void navigateTo(Position destination) {
        System.out.println("Taxi " + getName() + " navigating to [" + destination.getLatitude() + "," + destination.getLongitude() + "]");
    }
}
