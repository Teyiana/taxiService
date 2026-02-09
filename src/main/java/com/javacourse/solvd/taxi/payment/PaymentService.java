package com.javacourse.solvd.taxi.payment;

import com.javacourse.solvd.taxi.DataBase;

public abstract class PaymentService<P extends Payment> implements Processable {
    private final DataBase dataBase;

    protected PaymentService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public abstract void payForTrip(Payment payment);
    public abstract P preparePayment();

    public DataBase getDataBase() {
        return dataBase;
    }
}

