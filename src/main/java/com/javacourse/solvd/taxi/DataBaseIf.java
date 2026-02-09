package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.payment.Payment;
import com.javacourse.solvd.taxi.vehicle.Vehicle;

import java.util.List;

public interface DataBaseIf {


    MyLinkedList<Vehicle> getVehicle();

    List<Client> getClients();

    List<Trip> getTrips();

    public List<Payment> getPayment();

    public List<Payment> getPayments();

    public void addVehicle(Vehicle vehicle);

    public void addClient(Client client);

    public void addTrip(Trip trip);

    public void addPayment(Payment payment);

}
