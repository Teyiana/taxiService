package com.javacourse.solvd.taxi;

import com.javacourse.solvd.taxi.client.Client;
import com.javacourse.solvd.taxi.client.DeliveryClient;
import com.javacourse.solvd.taxi.client.Passenger;
import com.javacourse.solvd.taxi.client.CargoClient;
import com.javacourse.solvd.taxi.payment.Payment;
import com.javacourse.solvd.taxi.vehicle.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private static final Logger LOGGER = LogManager.getLogger(TripService.class);

    private static final double FOOD_CARGO_FACTOR = 0.1;
    private static final double FURNITURE_CARGO_FACTOR = 0.2;
    private static final double BUILDING_MATERIALS_CARGO_FACTOR = 0.3;
    private static final double OTHER_CARGO_FACTOR = 0.15;
    private static final double LOAD_WEIGHT_STEP_KG = 1000.0;

    private final DataBase dataBase;

    public TripService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Trip prepareTrip(Client client, Position dropOffLocation, Payment payment) {
        if (client instanceof Passenger) {
            Passenger passenger = (Passenger) client;
            LOGGER.info("Preparing trip for passenger: {}", passenger);
            return prepareTaxiTrip(passenger, dropOffLocation, payment);
        } else if (client instanceof CargoClient) {
            CargoClient cargoClient = (CargoClient) client;
            LOGGER.info("Preparing trip for truck: {}", cargoClient);
            return prepareCargoTrip(cargoClient, dropOffLocation, payment);
        } else if (client instanceof DeliveryClient) {
            DeliveryClient deliveryClient = (DeliveryClient) client;
            LOGGER.info("Preparing trip for delivery: {}",deliveryClient);
            return prepareDeliveryTrip(deliveryClient, dropOffLocation, payment);
        }
        throw new IllegalArgumentException("Unknown client type");
    }

    private Trip prepareDeliveryTrip(DeliveryClient deliveryClient, Position dropOffLocation, Payment payment) {
        Vehicle vehicle = findAvailableDelivery(deliveryClient);
        if (vehicle instanceof Delivery) {
            Delivery delivery = (Delivery) vehicle;
            double distance = calculateDistance(deliveryClient.getCurrentPosition(), dropOffLocation);
            LOGGER.info("Calculated trip distance for delivery: {}", distance);
            double paymentAmount = calculatePaymentForDelivery(distance, deliveryClient.getPackageWeight());
            LOGGER.info("Calculated payment amount for delivery: {}", paymentAmount);
            Trip trip = new Trip(delivery, deliveryClient, deliveryClient.getCurrentPosition(), dropOffLocation, payment.setAmount(paymentAmount));
            return trip;
        }
        LOGGER.info("No available delivery vehicles for client: {}",deliveryClient);
        return null;
    }

    private Vehicle findAvailableDelivery(DeliveryClient deliveryClient) {
        List<Delivery> availableDeliveries = new ArrayList<>();
        for (Vehicle vehicle : getDataBase().getVehicle()) {
            if (!vehicle.isBusy() && vehicle instanceof Delivery) {
                Delivery delivery = (Delivery) vehicle;
                availableDeliveries.add(delivery);
            }
        }
        Position clientPosition = deliveryClient.getCurrentPosition();
        availableDeliveries.sort((d1, d2) -> {
            double dist1 = calculateDistance(d1.getCurrentPosition(), clientPosition);
            double dist2 = calculateDistance(d2.getCurrentPosition(), clientPosition);
            return Double.compare(dist1, dist2);
        });
        if (!availableDeliveries.isEmpty()) {
            return availableDeliveries.get(0);
        }
        return null;
    }

    private double calculatePaymentForDelivery(double distance, double packageWeight) {
        double basePrice = getDataBase().getBasePrice();
        double weightFactor = 1 + (packageWeight / 100);
        return basePrice * distance * weightFactor;
    }

    private Trip prepareCargoTrip(CargoClient cargoClient, Position dropOffLocation, Payment payment) {
        Vehicle vehicle = findAvailableCargoTransport(cargoClient);
        if (vehicle instanceof CargoTransport) {
            CargoTransport cargoTransport = (CargoTransport) vehicle;
            double distance = calculateDistance(cargoClient.getCurrentPosition(), dropOffLocation);
            LOGGER.info("Calculated trip distance for cargo: {}",distance);
            double paymentAmount = calculatePaymentAmountForCargo(distance, cargoTransport.getCargoType(),
                    cargoTransport.getCurrentLoad());
            LOGGER.info("Calculated payment amount for cargo: {}", paymentAmount);
            payment.setAmount(paymentAmount);
            Trip trip = new Trip(cargoTransport, cargoClient, cargoClient.getCurrentPosition(), dropOffLocation, payment);
            return trip;
        }
        LOGGER.info("No available cargo transports for client: {}", cargoClient);
        return null;
    }

    private Vehicle findAvailableCargoTransport(CargoClient cargoClient) {
        List<CargoTransport> availableTransports = new ArrayList<>();
        for (Vehicle vehicle : getDataBase().getVehicle()) {
            if (!vehicle.isBusy() && vehicle instanceof CargoTransport) {
                CargoTransport cargoTransport = (CargoTransport) vehicle;
                availableTransports.add(cargoTransport);
            }
        }
        Position clientPosition = cargoClient.getCurrentPosition();
        availableTransports.sort((d1, d2) -> {
            double dist1 = calculateDistance(d1.getCurrentPosition(), clientPosition);
            double dist2 = calculateDistance(d2.getCurrentPosition(), clientPosition);
            return Double.compare(dist1, dist2);
        });
        if (!availableTransports.isEmpty()) {
            return availableTransports.get(0);
        }
        return null;
    }

    private Trip prepareTaxiTrip(Passenger passenger, Position dropOffLocation, Payment payment) {
        Vehicle vehicle = findAvailableTaxi(passenger);
        if (vehicle instanceof Taxi) {
            Taxi taxi = (Taxi) vehicle;
            double distance = calculateDistance(passenger.getCurrentPosition(), dropOffLocation);
            LOGGER.info("Calculated trip distance: {}", distance);
            double paymentAmount = calculatePaymentAmount(distance, passenger.getRating(), taxi.getRating());
            LOGGER.info("Calculated payment amount: {}", paymentAmount);
            payment.setAmount(paymentAmount);
            Trip trip = new Trip(taxi, passenger, passenger.getCurrentPosition(), dropOffLocation, payment);
            getDataBase().addTrip(trip);
            return trip;
        }
        LOGGER.info("No available drivers for passenger: {}", passenger);
        return null;

    }

    private double calculatePaymentAmountForCargo(double distance, CargoType cargoType, double currentLoad) {
        double basePrice = getDataBase().getBasePrice();
        double cargoFactor;

        switch (cargoType) {
            case FOOD:
                cargoFactor = FOOD_CARGO_FACTOR;
                break;
            case FURNITURE:
                cargoFactor = FURNITURE_CARGO_FACTOR;
                break;
            case BUILDING_MATERIALS:
                cargoFactor = BUILDING_MATERIALS_CARGO_FACTOR;
                break;
            case OTHER:
                cargoFactor = OTHER_CARGO_FACTOR;
                break;
            default:
                cargoFactor = 1.0;
        }
        double loadFactor = 1 + (currentLoad / LOAD_WEIGHT_STEP_KG);
        return basePrice * distance * cargoFactor * loadFactor;
    }

    private double calculatePaymentAmount(double distance, double passengerRating, double driverRating) {
        double basePrice = getDataBase().getBasePrice();
        double ratingFactor = (5 + driverRating) / (5 + passengerRating);
        return basePrice * distance * ratingFactor;
    }

    private double calculateDistance(Position currentPosition, Position dropOffLocation) {
        double latDiff = currentPosition.latitude() - dropOffLocation.latitude();
        double lonDiff = currentPosition.longitude() - dropOffLocation.longitude();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

    public Trip approveTrip(Trip trip) {
        trip.getVehicle().setBusy(true);
        trip.setTripStatus(TripStatus.WAITING_FOR_DRIVER);
        return trip;
    }

    public Trip startTrip(Trip trip) {
        trip.getVehicle().setCurrentPosition(trip.getPickupLocation());
        trip.setTripStatus(TripStatus.IN_PROGRESS);
        return trip;
    }

    public Trip completeTrip(Trip trip) {
        if (trip.getPayment() == null) {
            throw new IllegalStateException("Payment not completed");
        }
        trip.setTripStatus(TripStatus.COMPLETED);
        trip.getVehicle().setBusy(false);
        trip.getVehicle().setCurrentPosition(trip.getDropOffLocation());
        return trip;
    }

    private Taxi findAvailableTaxi(Passenger passenger) {
        List<Taxi> availableDrivers = new ArrayList<>();
        for (Vehicle vehicle : getDataBase().getVehicle()) {
            if (!vehicle.isBusy() && vehicle instanceof Taxi) {
                Taxi taxi = (Taxi) vehicle;
                availableDrivers.add(taxi);
            }
        }
        Position passengerPosition = passenger.getCurrentPosition();
        availableDrivers.sort((d1, d2) -> {
            double dist1 = calculateDistance(d1.getCurrentPosition(), passengerPosition);
            double dist2 = calculateDistance(d2.getCurrentPosition(), passengerPosition);
            return Double.compare(dist1, dist2);
        });
        if (!availableDrivers.isEmpty()) {
            return availableDrivers.get(0);
        }
        return null;
    }

    public DataBase getDataBase() {
        return dataBase;
    }
}
