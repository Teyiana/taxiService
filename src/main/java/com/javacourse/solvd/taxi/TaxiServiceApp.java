package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.CargoClient;
import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.client.DeliveryClient;
import com.javacourse.solvd.taxi.client.Passenger;
import com.javacourse.solvd.taxi.payment.*;
import com.javacourse.solvd.taxi.vehicle.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TaxiServiceApp {

    private DataBase db;
    private final Map<Class<? extends Payment>, PaymentService<?>> paymentServices = new HashMap<>();
    private TripService tripService;
    private Scanner scanner;

    public static void main(String[] args) {

        TaxiServiceApp taxiServiceApp = new TaxiServiceApp();
        taxiServiceApp.run();
    }

    private void run() {
        initData();

        println("Welcome!\nYou are welcomed by Taxi Service.");
        scanner = new Scanner(System.in);

        Client client = authorizeClient();

        Position endPosition = readPosition(client);
        Payment payment = choosePayment();
        tripFlow(client, endPosition, payment);
    }

    private Payment choosePayment() {
        Payment payment = null;
        while (payment == null) {
            println("Please select payment type: (card - c/cash - ca/invoice - i)");
            String answer = scanner.nextLine();
            try {
                payment = switch (answer) {
                    case "c", "card" -> paymentServices.get(CardPayment.class).preparePayment();
                    case "ca", "cash" -> paymentServices.get(CashPayment.class).preparePayment();
                    case "i", "invoice" -> paymentServices.get(InvoicePayment.class).preparePayment();
                    default -> null;
                };
            } catch (Exception e) {
                println("Invalid input. Please enter the correct data.");
            }
        }
        return payment;
    }

    private Position readPosition(Client client) {
        try {
            println("Please enter start position coordinates");

            println("Please enter latitude:");
            double latitude = scanner.nextDouble();

            println("Please enter longitude:");
            double longitude = scanner.nextDouble();
            scanner.nextLine();

            client.getCurrentPosition().setLatitude(latitude);
            client.getCurrentPosition().setLongitude(longitude);

            println("Please enter ending position coordinates");

            println("Please enter latitude:");
            latitude = scanner.nextDouble();

            println("Please enter longitude:");
            longitude = scanner.nextDouble();
            return new Position(latitude, longitude);
        } catch (Exception e) {
            println("Invalid input. Please enter the correct coordinates.");
            return readPosition(client);
        }
    }


    private Client authorizeClient() {
        Client client = null;
        while (client == null) {
            println("Do you want to register new client or login to existing one? (register - y/n)");
            String answer = scanner.nextLine();

            try {
                if (answer.equals("y") || answer.equals("yes")) {
                    client = registerNewClient();
                } else if (answer.equals("n") || answer.equals("no")) {
                    client = loginClient();
                }
            } catch (Exception e) {
                println("Invalid input. Please enter the correct data.");
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

            println("Your trip is prepared. Detailed information:\n\n" + trip.getVehicle() + trip.getPayment().getAmount());
            println("Do you want to approve trip? (yes/no)");
            String answer = scanner.nextLine();
            if (answer.equals("yes") || answer.equals("y")) {
                tripService.approveTrip(trip);
            } else if (answer.equals("no") || answer.equals("n")) {
                println("Trip not approved.");
                return;
            }
            println("Do you want to start trip? (yes/no)");
            answer = scanner.nextLine();
            if (answer.equals("yes") || answer.equals("y")) {
                tripService.startTrip(trip);
            } else if (answer.equals("no") || answer.equals("n")) {
                return;
            }
            println("Please payment complete for trip. (yes/no)");
            answer = scanner.nextLine();
            if (answer.equals("yes") || answer.equals("y")) {
                paymentServices.get(payment.getClass()).payForTrip(payment);
                println("Your trip completed.");
                tripService.completeTrip(trip);
            } else if (answer.equals("no") || answer.equals("n")) {
                trip.getPayment().setStatus(false);
                println("Trip cancel.");
            }
        } catch (Exception e) {
            println("Trip preparation failed. Reason: " + e.getMessage());
        }

    }


    private Client loginClient() {
        println("Please enter phone number:");
        String phone = scanner.nextLine().trim();
        try {
            for (Client client : db.getClients()) {
                if (client.getPhoneNumber().equals(phone)) {
                    println("Client logged in: " + client);
                    return client;
                }
            }

        } catch (Exception e) {
            println("Invalid input. Please enter the correct phone number.");
        }

        println("Client with phone number " + phone + " not found.");
        println("Please try again.");
        println("Do you want to register? (yes / no)");
        String answer = scanner.nextLine();
        if (answer.equals("yes")) {
            Client newClient = registerNewClient();
            db.getClients().add(newClient);
        }

        return null;
    }

    private Client registerNewClient() {
        try {
            println("Please enter client type:");
            println("passenger - p | delivery - d | cargo - c");
            String answer = scanner.nextLine();

            println("Enter name: ");
            String name = scanner.nextLine();

            println("Enter phone number: ");
            String phone = scanner.nextLine();

            Position currentPosition = new Position(0, 0);

            switch (answer) {
                case "p":
                    return new Passenger(name, phone, currentPosition);

                case "d":
                    println("Enter package weight: ");
                    double weight = Double.parseDouble(scanner.nextLine());
                    return new DeliveryClient(name, phone, currentPosition, weight);

                case "c":
                    return new CargoClient(name, phone, currentPosition);

                default:
                    println("Invalid client type. Try again.");
                    return registerNewClient();
            }

        } catch (NumberFormatException e) {
            println("Invalid input. Please enter the correct data.");
            return registerNewClient();
        }
    }


    private void initData() {
        db = new DataBase(15.2);

        paymentServices.put(CardPayment.class, new CardPaymentService(db));
        paymentServices.put(CashPayment.class, new CashPaymentService(db));
        paymentServices.put(InvoicePayment.class, new InvoicePaymentService(db));

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

    public static void println(String msg) {
        System.out.println(msg);
    }
}
