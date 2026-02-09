package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public class CardPaymentService extends PaymentService<CardPayment>  {

    public CardPaymentService(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    public void payForTrip(Payment payment) {
        payment.setStatus(true);
        System.out.println("Card payment processed: " + payment);
    }

    @Override
    public CardPayment preparePayment() {
        return new CardPayment();
    }

    @Override
    public void processPayment(Payment payment) {
        payment.setStatus(true);
        System.out.println("Card payment processed: " + payment);
    }
}
