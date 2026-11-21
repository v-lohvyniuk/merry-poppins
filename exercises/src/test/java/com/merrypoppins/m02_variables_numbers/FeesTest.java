package com.merrypoppins.m02_variables_numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FeesTest {

    @Test
    void feeIsAPercentOfTheAmount() {
        assertEquals(200, Fees.transferFeeCents(10000, 2),
                "A 2 percent fee on 10000 cents should be 200 cents");
    }

    @Test
    void feeIsRoundedDownToWholeCents() {
        assertEquals(59, Fees.transferFeeCents(1999, 3),
                "A 3 percent fee on 1999 cents is 59.97, so rounded down it should be 59 cents");
    }

    @Test
    void feeForAnotherAmount() {
        assertEquals(25, Fees.transferFeeCents(2550, 1),
                "A 1 percent fee on 2550 cents is 25.5, so rounded down it should be 25 cents");
    }

    @Test
    void eachPersonPaysAnEqualShare() {
        assertEquals(250, Fees.splitBillShareCents(1000, 4),
                "1000 cents split between 4 people should be 250 cents each");
    }

    @Test
    void shareIsRoundedDownWhenItDoesNotDivideEvenly() {
        assertEquals(333, Fees.splitBillShareCents(1000, 3),
                "1000 cents split between 3 people is 333.33, so rounded down it should be 333 cents each");
    }

    @Test
    void leftoverIsWhatRemainsAfterSharing() {
        assertEquals(1, Fees.splitBillLeftoverCents(1000, 3),
                "1000 cents split between 3 people: 3 x 333 = 999, so 1 cent should be left over");
    }

    @Test
    void leftoverIsZeroWhenItDividesEvenly() {
        assertEquals(0, Fees.splitBillLeftoverCents(1000, 4),
                "1000 cents split between 4 people divides evenly, so 0 cents should be left over");
    }
}
