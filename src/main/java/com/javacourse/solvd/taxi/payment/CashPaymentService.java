package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public class CashPaymentService extends PaymentService<CashPayment> {

    public CashPaymentService(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    public void payForTrip(Payment payment) {
        payment.setStatus(true);
        System.out.println("Cash payment processed: " + payment);
    }

    @Override
    public CashPayment preparePayment() {
        return new CashPayment();
    }

    @Override
    public void processPayment(Payment payment) {
        payment.setStatus(true);
        System.out.println("Cash payment processed: " + payment);
    }
}
