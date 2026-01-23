package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public class CardPayment extends PaymentService {

    public CardPayment(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    public void payForTrip(Payment payment) {
        payment.setStatus(true);
        System.out.println("Card payment processed: " + payment);
    }
}
