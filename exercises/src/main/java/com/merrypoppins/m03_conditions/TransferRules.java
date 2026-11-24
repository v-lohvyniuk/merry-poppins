package com.merrypoppins.m03_conditions;

/**
 * Exercise 1: yes/no checks for a money transfer.
 *
 * Each method answers a yes/no question, so it hands back a boolean (true or false).
 * Each one returns false for now. Replace false with the right check.
 * All amounts are in cents.
 */
public class TransferRules {

    /**
     * Is there enough money? Yes if the balance is at least the amount.
     * Exactly enough counts as enough.
     */
    static boolean hasEnoughMoney(int balanceCents, int amountCents) {
        // TODO: compare the balance with the amount
        return false;
    }

    /**
     * Is the amount within the daily limit? Yes if it is not more than the limit.
     * Exactly the limit is still OK.
     */
    static boolean isWithinDailyLimit(int amountCents, int dailyLimitCents) {
        // TODO: compare the amount with the limit
        return false;
    }

    /**
     * Can the transfer go ahead? Only if ALL three are true:
     *   - there is enough money (exactly enough is enough),
     *   - the amount is within the daily limit (exactly the limit is OK),
     *   - the card is NOT blocked.
     */
    static boolean canTransfer(int balanceCents, int amountCents, int dailyLimitCents, boolean cardBlocked) {
        // TODO: combine the three rules
        return false;
    }
}
