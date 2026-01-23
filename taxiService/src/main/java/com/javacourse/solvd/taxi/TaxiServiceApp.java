package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.CargoClient;
import com.javacourse.solvd.taxi.client.DeliveryClient;
import com.javacourse.solvd.taxi.client.Passenger;
import com.javacourse.solvd.taxi.payment.*;
import com.javacourse.solvd.taxi.vehicle.*;


public class TaxiServiceApp {

    public static void main(String[] args) {
        DataBase db = new DataBase(15.2);

        PaymentService cardPayment = new CardPayment(db);
        PaymentService cashPayment = new CashPayment(db);
        PaymentService invoicePayment = new InvoicePayment(db);


        TripService tripService = new TripService(db);


        Vehicle taxi = new EconomyClass("Ivan", "+30501112233", new Position(48.8566, 2.3522));
        Vehicle delivery = new CargoDelivery("Mykola", "+30504455667", new Position(51.5074, -0.1278));
        Vehicle cargoTransport = new Truck("Oleg", "+30507889900", 10.0, new Position(34.0522, -118.2437), CargoType.BUILDING_MATERIALS);


        db.addVehicle(taxi);
        db.addVehicle(delivery);
        db.addVehicle(cargoTransport);


        Passenger passenger = new Passenger("Olga", "097-567-08-12", new Position(40.7128, -74.0060));
        db.addClient(passenger);


        DeliveryClient deliveryClient = new DeliveryClient("DHL", "123-456-7890", new Position(37.7749, -122.4194), 5.0);
        db.addClient(deliveryClient);

        CargoClient cargoClient = new CargoClient("ACME Corp", "987-654-3210", new Position(25.7617, -80.1918));
        db.addClient(cargoClient);

        System.out.println("Taxi Service is up and running!");

        Trip tripEconomy = tripService.prepareTrip(passenger, new Position(34.0522, -118.2437), cardPayment.preparePayment(PaymentType.CARD));
        System.out.println("Trip prepared: " + tripEconomy);
        tripService.approveTrip(tripEconomy);
        System.out.println("Trip approved " + tripEconomy);
        tripService.startTrip(tripEconomy);
        System.out.println("Trip started: " + tripEconomy);
        cardPayment.payForTrip(tripEconomy.getPayment());
        System.out.println("Payment completed for trip:: " + tripEconomy);
        tripService.completeTrip(tripEconomy);
        System.out.println("Trip completed: " + tripEconomy);


        Trip tripDelivery = tripService.prepareTrip(deliveryClient, new Position(41.8781, -87.6298), cashPayment.preparePayment(PaymentType.CASH));
        tripService.approveTrip(tripDelivery);
        System.out.println("Trip prepared: " + tripDelivery);


        Trip tripCargo = tripService.prepareTrip(cargoClient, new Position(29.7604, -95.3698), invoicePayment.preparePayment(PaymentType.INVOICE));
        tripService.approveTrip(tripCargo);
        System.out.println("Trip prepared: " + tripCargo);


    }
}
