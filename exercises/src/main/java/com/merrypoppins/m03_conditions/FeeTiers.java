package com.merrypoppins.m03_conditions;

/**
 * Exercise 3: the fee percent depends on how big the transfer is.
 *
 * All amounts are in cents (10000 cents = 100.00 EUR).
 */
public class FeeTiers {

    /**
     * The fee percent for a transfer:
     *
     *   less than 10000 cents (under 100 EUR)                  ->  2
     *   from 10000 up to 99999 cents (100 EUR to 999.99 EUR)   ->  1
     *   100000 cents or more (1000 EUR or more)                ->  0
     */
    static int feePercent(int amountCents) {
        int percent; 
        if(amountCents < 10000){
           percent = 2;
        } else if (amountCents <= 99999){
            percent = 1;
        } else {
            percent = 0;
        }
        return percent;
    }
}
