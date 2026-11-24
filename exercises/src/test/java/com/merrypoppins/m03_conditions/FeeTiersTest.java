package com.merrypoppins.m03_conditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FeeTiersTest {

    private void check(int amountCents, int expectedPercent, String why) {
        assertEquals(expectedPercent, FeeTiers.feePercent(amountCents),
                "feePercent(" + amountCents + " cents) should be " + expectedPercent + ": " + why);
    }

    @Test
    void smallAmountPaysTwoPercent() {
        check(50, 2, "0.50 EUR is under 100 EUR");
    }

    @Test
    void justBelowOneHundredEurosPaysTwoPercent() {
        check(9999, 2, "99.99 EUR is still under 100 EUR");
    }

    @Test
    void exactlyOneHundredEurosPaysOnePercent() {
        check(10000, 1, "exactly 100 EUR is in the 1 percent group");
    }

    @Test
    void justBelowOneThousandEurosPaysOnePercent() {
        check(99999, 1, "999.99 EUR is still under 1000 EUR");
    }

    @Test
    void exactlyOneThousandEurosPaysNothing() {
        check(100000, 0, "exactly 1000 EUR is in the no-fee group");
    }

    @Test
    void largeAmountPaysNothing() {
        check(500000, 0, "5000 EUR is 1000 EUR or more");
    }
}
