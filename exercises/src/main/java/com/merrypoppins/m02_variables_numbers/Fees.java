package com.merrypoppins.m02_variables_numbers;

/**
 * Exercise 2: fees and bill splitting, in cents.
 *
 * Many payment systems store money as whole cents (an int): 19.99 EUR is 1999 cents.
 * Each method below returns 0 for now. Replace the 0 with the right calculation.
 */
public class Fees {

    /**
     * The transfer fee in cents: feePercent percent of amountCents, rounded DOWN to whole cents.
     *
     * Example: 10000 cents with a 2 percent fee  ->  200 cents
     * Example: 1999 cents with a 3 percent fee   ->  59 cents (59.97 rounded down)
     */
    static int transferFeeCents(int amountCents, int feePercent) {
        // TODO: calculate the fee
        return 0;
    }

    /**
     * Friends split a bill evenly. How many cents does each person pay? Rounded DOWN.
     *
     * Example: 1000 cents, 4 people  ->  250 cents each
     * Example: 1000 cents, 3 people  ->  333 cents each
     */
    static int splitBillShareCents(int totalCents, int people) {
        // TODO: calculate one person's share
        return 0;
    }

    /**
     * After everyone pays their (rounded down) share, how many cents are left over?
     *
     * Example: 1000 cents, 3 people  ->  1 cent left over  (3 x 333 = 999)
     * Example: 1000 cents, 4 people  ->  0 cents left over
     */
    static int splitBillLeftoverCents(int totalCents, int people) {
        // TODO: calculate what is left over
        return 0;
    }
}
