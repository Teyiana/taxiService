package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;
import lombok.Data;

@Data
public class Passenger extends Client {

    private double rating = 5.0;

    public Passenger(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

    @Override
    public void createOrder() {
        System.out.println("Passenger" + getName() + " created a taxi order from position: " +
                getCurrentPosition().longitude() + ", " +
                getCurrentPosition().toString());
    }

}
