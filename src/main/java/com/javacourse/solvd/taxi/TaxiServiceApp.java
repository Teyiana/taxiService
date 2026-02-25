package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.CargoClient;
import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.client.DeliveryClient;
import com.javacourse.solvd.taxi.client.Passenger;
import com.javacourse.solvd.taxi.payment.*;
import com.javacourse.solvd.taxi.vehicle.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TaxiServiceApp {

    private static final Logger LOGGER = LogManager.getLogger(TaxiServiceApp.class);

    private DataBase db;
    private PaymentService paymentService;
    private TripService tripService;
    private Scanner scanner;

    public static void main(String[] args) {

        TaxiServiceApp taxiServiceApp = new TaxiServiceApp();
        taxiServiceApp.run();
    }

    private void run() {
        initData();

        LOGGER.info("Welcome!\nYou are welcomed by Taxi Service.");
        scanner = new Scanner(System.in);

        Client client = authorizeClient();

        Position endPosition = readPosition(client);
        Payment payment = choosePayment();
        tripFlow(client, endPosition, payment);
    }

    private Payment choosePayment() {
        Payment payment = null;
        while (payment == null) {
            LOGGER.info("Please select payment type: card(c), cash(ca), invoice(in)");
            String answer = scanner.nextLine().trim();
            try {
                switch (answer) {
                    case "card", "c": {
                        payment = paymentService.preparePayment(PaymentType.CARD);
                        break;
                    }
                    case "cash", "ca": {
                        payment = paymentService.preparePayment(PaymentType.CASH);
                        break;
                    }
                    case "invoice", "in": {
                        payment = paymentService.preparePayment(PaymentType.INVOICE);
                        break;
                    }
                    default:
                        LOGGER.info("Invalid payment type. Try again.");
                }
            } catch (Exception e) {
                LOGGER.error("Invalid input. Please enter the correct payment type.");
            }
        }
        return payment;
    }

    private Position readPosition(Client client) {
        try {
            LOGGER.info("Please enter start position coordinates");

            LOGGER.info("Please enter latitude:");
            double latitude = scanner.nextDouble();

            LOGGER.info("Please enter longitude:");
            double longitude = scanner.nextDouble();
            scanner.nextLine();

            client.setCurrentPosition(new Position(latitude, longitude));

            LOGGER.info("Please enter ending position coordinates");

            LOGGER.info("Please enter latitude:");
            latitude = scanner.nextDouble();

            LOGGER.info("Please enter longitude:");
            longitude = scanner.nextDouble();
            return new Position(latitude, longitude);
        } catch (Exception e) {
            LOGGER.error("Invalid input. Please enter the correct coordinates.");
            return readPosition(client);
        }
    }


    private Client authorizeClient() {
        Client client = null;
        while (client == null) {
            LOGGER.info("Do you want to register new client or login to existing one? (register - y/n)");
            String answer = scanner.nextLine();

            try {
                if (answer.equals("y") || answer.equals("yes")) {
                    client = registerNewClient();
                } else if (answer.equals("n") || answer.equals("no")) {
                    client = loginClient();
                }
            } catch (Exception e) {
                LOGGER.error("Invalid input. Please enter the correct data.");
            }
        }
        return client;
    }


    private void tripFlow(Client client, Position endPosition, Payment payment) {
        try {
            Trip trip = tripService.prepareTrip(client, endPosition, payment);

            if (trip == null) {
                throw new IllegalStateException("No available vehicles for trip");
            }

            LOGGER.info("Your trip is prepared. Detailed information:\n\n {} {}" , trip.getVehicle(), trip.getPayment().amount());
            LOGGER.info("Do you want to approve trip? (yes/no)");
            String answer = scanner.nextLine();
            if (answer.equals("yes") || answer.equals("y")) {
                tripService.approveTrip(trip);
            } else if (answer.equals("no") || answer.equals("n")) {
                LOGGER.info("Trip not approved.");
                return;
            }
            LOGGER.info("Do you want to start trip? (yes/no)");
            answer = scanner.nextLine();
            if (answer.equals("yes") || answer.equals("y")) {
                tripService.startTrip(trip);
            } else if (answer.equals("no") || answer.equals("n")) {
                return;
            }
            LOGGER.info("Please payment complete for trip. (yes/no)");
            answer = scanner.nextLine();
            if (answer.equals("yes") || answer.equals("y")) {
                paymentService.payForTrip(trip.getPayment());
                LOGGER.info("Your trip completed.");
                tripService.completeTrip(trip);
            } else if (answer.equals("no") || answer.equals("n")) {
                trip.setPayment(null);
                LOGGER.info("Trip cancel.");
            }
        } catch (Exception e) {
            LOGGER.error("Trip preparation failed.");
        }

    }


    private Client loginClient() {
        LOGGER.info("Please enter phone number:");
        String phone = scanner.nextLine().trim();
        try {
            for (Client client : db.getClients()) {
                if (client.getPhoneNumber().equals(phone)) {
                    LOGGER.info("Client logged in: {}", client);
                    return client;
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error during login.", e);
        }

        LOGGER.info("Client with phone number {} not found.", phone);
        LOGGER.info("Please try again.");
        LOGGER.info("Do you want to register? (yes / no)");
        String answer = scanner.nextLine();
        if (answer.equals("yes")) {
            Client newClient = registerNewClient();
            db.getClients().add(newClient);
        }

        return null;
    }

    private Client registerNewClient() {
        try {
            LOGGER.info("Please enter client type:");
            LOGGER.info("passenger - p | delivery - d | cargo - c");
            String answer = scanner.nextLine();

            LOGGER.info("Enter name: ");
            String name = scanner.nextLine();

            LOGGER.info("Enter phone number: ");
            String phone = scanner.nextLine();

            Position currentPosition = new Position(0, 0);

            switch (answer) {
                case "p":
                    return new Passenger(name, phone, currentPosition);

                case "d":
                    LOGGER.info("Enter package weight: ");
                    double weight = Double.parseDouble(scanner.nextLine());
                    return new DeliveryClient(name, phone, currentPosition, weight);

                case "c":
                    return new CargoClient(name, phone, currentPosition);

                default:
                    LOGGER.info("Invalid client type. Try again.");
                    return registerNewClient();
            }

        } catch (NumberFormatException e) {
            LOGGER.error("Invalid input. Please enter the correct client type.");
            return registerNewClient();
        }
    }


    private void initData() {
        db = new DataBase(15.2);

        paymentService = new PaymentService(db);

        tripService = new TripService(db);


        db.addVehicle(new EconomyClass("Ivan", "+30501112233", new Position(48.8566, 2.3522)));
        db.addVehicle(new BusinessClass("Petro", "6456-898-900", new Position(42.8566, 2.5522)));
        db.addVehicle(new CourierDelivery("Yura", "097-567-08-12", new Position(40.7128, -74.0060)));
        db.addVehicle(new CargoDelivery("Mykola", "+30504455667", new Position(52.5074, -0.2278)));
        db.addVehicle(new Shipment("Oleg", "+30507889900", 10.0, new Position(34.0522, -118.2437), CargoType.BUILDING_MATERIALS));


        db.addClient(new Passenger("Olga", "097-567-08-12", new Position(40.7128, -74.0060)));
        db.addClient(new DeliveryClient("DHL", "123-456-7890", new Position(37.7749, -122.4194), 5.0));
        db.addClient(new CargoClient("ACME Corp", "987-654-3210", new Position(25.7617, -80.1918)));
    }

}
