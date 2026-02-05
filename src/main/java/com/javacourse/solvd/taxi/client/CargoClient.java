package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;

public class CargoClient extends Client {

    public CargoClient(String name, String phoneNumber, Position currentPosition) {
        super(name, phoneNumber, currentPosition);
    }

 @Override
    public void createOrder() {
        System.out.println("Cargo client " + getName() + " created a cargo transport order.");
    }
}
