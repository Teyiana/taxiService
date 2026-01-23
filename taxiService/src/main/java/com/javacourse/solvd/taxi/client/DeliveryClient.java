package com.javacourse.solvd.taxi.client;

import com.javacourse.solvd.taxi.Position;

public class DeliveryClient extends Client {
    private double packageWeight;

    public DeliveryClient(String name, String phoneNumber, Position currentPosition, double packageWeight) {
        super(name, phoneNumber, currentPosition);
        this.packageWeight = packageWeight;
    }

    public double getPackageWeight() {
        return packageWeight;
    }

    @Override
    public void createOrder() {
        System.out.println("Delivery client " + getName() + " created a delivery order. Package weight: " +
                packageWeight + " kg");
    }
}
