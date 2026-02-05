package com.javacourse.solvd.taxi.payment;

public class Payment {
    private double amount;
    private PaymentType type;
    private boolean status = false;

    public Payment(PaymentType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }


    public PaymentType getType() {
        return type;
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
                ", type=" + type +
                ", status=" + status +
                '}';
    }

    public void setAmount(double paymentAmount) {
        this.amount = paymentAmount;
    }
}
