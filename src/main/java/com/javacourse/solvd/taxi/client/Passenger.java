package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;

public class Passenger extends Client {
    private double rating = 5.0;

    public Passenger(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public void createOrder() {
        System.out.println("Passenger" + getName() + " created a taxi order from position: " +
                getCurrentPosition().getLatitude() + ", " +
                getCurrentPosition().getLongitude());
    }

}
