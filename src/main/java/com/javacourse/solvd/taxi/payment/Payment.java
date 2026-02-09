package com.javacourse.solvd.taxi.payment;

public class Payment {
    private double amount;

    private boolean status = false;

    public double getAmount() {
        return amount;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", status=" + status +
                '}';
    }

    public void setAmount(double paymentAmount) {
        this.amount = paymentAmount;
    }
}
