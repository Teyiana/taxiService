package com.javacourse.solvd.taxi.vehicle;

import com.javacourse.solvd.taxi.Position;

public class BusinessClass extends Taxi {

    public BusinessClass(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    protected void navigateTo(Position destination) {
        System.out.println("Taxi " + getName() + " navigating to [" +
                destination.latitude() + ", " +
                destination.longitude() + "]");
    }
}
