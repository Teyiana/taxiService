package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public class CashPayment extends PaymentService {

    public CashPayment(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    public void payForTrip(Payment payment) {
        payment.setStatus(true);
        System.out.println("Cash payment processed: " + payment);
    }
}
