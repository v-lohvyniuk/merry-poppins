package com.merrypoppins.m02_variables_numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CashbackTest {

    /** Decimals can be off by a tiny bit in a computer, so "close enough" means within 0.0001. */
    private static final double CLOSE_ENOUGH = 0.0001;

    @Test
    void cashbackIsAPercentOfTheAmount() {
        assertEquals(3.0, Cashback.cashback(200.0, 1.5), CLOSE_ENOUGH,
                "1.5 percent cashback on 200.00 should be 3.00");
    }

    @Test
    void cashbackKeepsTheDecimals() {
        assertEquals(1.1998, Cashback.cashback(59.99, 2.0), CLOSE_ENOUGH,
                "2 percent cashback on 59.99 should be 1.1998");
    }

    @Test
    void averagePaymentKeepsTheDecimals() {
        assertEquals(2.5, Cashback.averagePayment(10.0, 4), CLOSE_ENOUGH,
                "10.00 in total over 4 payments should be an average of 2.5");
    }

    @Test
    void averagePaymentForAnotherCustomer() {
        assertEquals(33.3333, Cashback.averagePayment(100.0, 3), CLOSE_ENOUGH,
                "100.00 in total over 3 payments should be an average of about 33.3333");
    }
}
