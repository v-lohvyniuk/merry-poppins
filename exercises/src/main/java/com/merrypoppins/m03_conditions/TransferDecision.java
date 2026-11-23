package com.merrypoppins.m03_conditions;

/**
 * Exercises 2 and 4: the bank's decision about a transfer, as text.
 *
 * These methods hand back text (the type is called String; you'll learn more about it in m04).
 * Return the text in double quotes, exactly as written below, e.g.  return "APPROVED";
 * All amounts are in cents.
 */
public class TransferDecision {

    /**
     * Exercise 2: "APPROVED" if the balance is at least the amount (exactly enough is enough),
     * otherwise "DECLINED".
     */
    static String decide(int balanceCents, int amountCents) {
        // TODO: use if / else
        return "";
    }

    /**
     * Exercise 4: why would this transfer be declined? Check the rules IN THIS ORDER
     * and hand back the first one that applies:
     *
     *   1. the card is blocked                        ->  "CARD_BLOCKED"
     *   2. the amount is more than the daily limit    ->  "OVER_DAILY_LIMIT"
     *   3. the balance is less than the amount        ->  "INSUFFICIENT_FUNDS"
     *   none of these                                 ->  "NONE"
     */
    static String declineReason(int balanceCents, int amountCents, int dailyLimitCents, boolean cardBlocked) {
        // TODO: use if / else if / else
        return "";
    }
}
