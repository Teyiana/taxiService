package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.payment.Payment;
import com.javacourse.solvd.taxi.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;


public class Trip {

    @Getter
    private final Vehicle vehicle;
    @Getter
    private final Client client;
    @Getter
    @Setter
    private Position pickupLocation;
    @Getter
    @Setter
    private Position dropOffLocation;
    @Getter
    @Setter
    private  Payment payment;

    private TripStatus tripStatus = TripStatus.NEW;

    public Trip(Vehicle vehicle, Client client, Position pickupLocation, Position dropOffLocation, Payment payment) {
        this.vehicle = vehicle;
        this.client = client;
        this.pickupLocation = pickupLocation;
        this.dropOffLocation = dropOffLocation;
        this.payment = payment;
    }


    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }



    @Override
    public String toString() {
        return "Trip{" +
                "driver=" + vehicle +
                ", passenger=" + client +
                ", pickupLocation=" + pickupLocation +
                ", dropOffLocation=" + dropOffLocation +
                ", payment=" + payment +
                ", tripStatus=" + tripStatus +
                '}';
    }


}
