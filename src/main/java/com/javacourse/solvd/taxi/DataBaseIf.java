package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.payment.Payment;
import com.javacourse.solvd.taxi.vehicle.Vehicle;

import java.util.List;

public interface DataBaseIf {


    MyLinkedList<Vehicle> getVehicle();

    List<Client> getClients();

    List<Trip> getTrips();

    List<Payment> getPayment();

    List<Payment> getPayments();

    void addVehicle(Vehicle vehicle);

    void addClient(Client client);

    void addTrip(Trip trip);

    void addPayment(Payment payment);

}
