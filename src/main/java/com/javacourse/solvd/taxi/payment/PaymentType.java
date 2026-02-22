package com.javacourse.solvd.taxi.payment;

import lombok.Getter;

public enum PaymentType {
    CASH("Cash"), CARD("Card"), INVOICE("Invoice");

    @Getter
    private final String name;

    PaymentType(String name) {
        this.name = name;
    }
}
