package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;

import lombok.Getter;
import lombok.Setter;

public abstract class Client {

    @Getter
    private final String name;
    @Getter
    private final String phoneNumber;
    @Getter
    @Setter
    private Position currentPosition;

    public Client(String name, String phoneNumber, Position currentPosition) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentPosition = currentPosition;
    }

    public abstract void createOrder();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", currentPosition=" + currentPosition +
                '}';
    }
}
