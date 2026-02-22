package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentService.class);
    @Getter
    private final DataBase dataBase;

    public PaymentService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void payForTrip(Payment payment) {
        payment.doPayment().pay();
        dataBase.addPayment(payment);
    }

    public Payment preparePayment(PaymentType paymentType) {
        switch (paymentType) {
            case CARD -> LOGGER.info("Payment with a card succeeded.");
            case CASH -> LOGGER.info("Payment with cash succeeded.");
            case INVOICE -> LOGGER.info("Payment with the invoice succeeded.");
            default -> throw new IllegalStateException("Unexpected value: " + paymentType);
        }
        return new Payment(0, () -> LOGGER.info("Payment with {} succeeded.", paymentType));
    }

}

