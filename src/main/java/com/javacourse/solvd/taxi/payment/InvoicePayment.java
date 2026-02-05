package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public class InvoicePayment extends PaymentService {

    public InvoicePayment(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    public void payForTrip(Payment payment) {
        System.out.println("Invoice issued: " + payment);
    }

    @Override
    public void processPayment(Payment payment) {
        payment.setStatus(true);
        System.out.println("Invoice payment processed: " + payment);
    }
}
