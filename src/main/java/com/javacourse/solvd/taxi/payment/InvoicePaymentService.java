package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public class InvoicePaymentService extends PaymentService<InvoicePayment> {

    public InvoicePaymentService(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    public void payForTrip(Payment payment) {
        payment.setStatus(true);
        System.out.println("Invoice issued: " + payment);
    }

    @Override
    public InvoicePayment preparePayment() {
        return new InvoicePayment();
    }

    @Override
    public void processPayment(Payment payment) {
        payment.setStatus(true);
        System.out.println("Invoice payment processed: " + payment);
    }
}
