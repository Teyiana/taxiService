package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.payment.Payment;
import com.javacourse.solvd.taxi.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataBase implements DataBaseIf {
    private static final Logger LOGGER = LogManager.getLogger(DataBase.class);

    private MyLinkedList<Vehicle> vehicles = new MyLinkedList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    @Getter
    @Setter
    private  double basePrice;

    public DataBase(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public MyLinkedList<Vehicle> getVehicle() {
        return vehicles;
    }

    @Override
    public List<Client> getClients() {
        return clients;
    }

    @Override
    public List<Trip> getTrips() {
        return trips;
    }

    @Override
    public List<Payment> getPayment() {
        return payments;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        LOGGER.info("DB: Adding driver: {}", vehicle);
        vehicles.add(vehicle);
    }

    @Override
    public void addClient(Client client) {
        LOGGER.info("DB: Adding client: {}", client);
        clients.add(client);
    }

    @Override
    public void addTrip(Trip trip) {
        trips.add(trip);
    }


    @Override
    public void addPayment(Payment payment) {
        LOGGER.info("DB: Adding payment: {}", payment);
        payments.add(payment);
    }

    @Override
    public List<Payment> getPayments() {
        return payments;
    }
}
