package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.payment.Payment;
import com.javacourse.solvd.taxi.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private List<Vehicle> drivers = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    private double basePrice;

    public DataBase(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<Vehicle> getDrivers() {
        return drivers;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void addVehicle(Vehicle vehicle) {
        System.out.println("DB: Adding driver: " + vehicle);
        drivers.add(vehicle);
    }

    public void addClient(Client client) {
        System.out.println("DB: Adding client: " + client);
        clients.add(client);
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void addPayment(Payment payment) {
        System.out.println("DB: Adding payment: " + payment);
        payments.add(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
