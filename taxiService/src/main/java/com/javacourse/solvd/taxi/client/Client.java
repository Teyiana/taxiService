package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;

public abstract class Client {
    private final String name;
    private final String phoneNumber;
    private Position currentPosition;

    public Client(String name, String phoneNumber, Position currentPosition) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentPosition = currentPosition;
    }

    public abstract void createOrder();

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", currentPosition=" + currentPosition +
                '}';
    }
}
