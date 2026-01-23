package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.client.Passenger;
import com.javacourse.solvd.taxi.payment.Payment;
import com.javacourse.solvd.taxi.vehicle.Taxi;
import com.javacourse.solvd.taxi.vehicle.Vehicle;

public class Trip {
    private final Vehicle vehicle;
    private final Client client;

    private Position pickupLocation;
    private Position dropOffLocation;
    private Payment payment;

    private TripStatus tripStatus = TripStatus.NEW;

    public Trip(Vehicle vehicle, Client client, Position pickupLocation, Position dropOffLocation, Payment payment) {
        this.vehicle = vehicle;
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.payment = payment;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Client getClient() {
        return client;
    }

    public Position getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Position pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Position getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(Position dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return "com.javacourse.solvd.taxi.Trip{" +
                "driver=" + vehicle +
                ", passenger=" + client +
                ", pickupLocation=" + pickupLocation +
                ", dropOffLocation=" + dropOffLocation +
                ", com.javacourse.solvd.taxi.payment=" + payment +
                ", tripStatus=" + tripStatus +
                '}';
    }
}
