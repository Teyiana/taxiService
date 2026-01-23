package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public abstract class PaymentService {
    private final DataBase dataBase;

    protected PaymentService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public abstract void payForTrip(Payment payment);


    public Payment preparePayment(PaymentType paymentType) {
        Payment payment = new Payment(paymentType);
        dataBase.addPayment(payment);
        return payment;
    }

    public DataBase getDataBase() {
        return dataBase;
    }
}

