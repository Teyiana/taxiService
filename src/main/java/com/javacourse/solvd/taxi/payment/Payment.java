package com.javacourse.solvd.taxi.payment;

public record Payment(double amount, Payable doPayment) {

    public Payment setAmount(double amount) {
        return new Payment(amount, this.doPayment);
    }
}
