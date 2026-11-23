package com.merrypoppins.m04_strings;

/**
 * Exercise 3: comparing text that comes back from the bank's API.
 *
 * In real tests, statuses and currencies arrive from an API response, not from your own code.
 * The tests here do the same, so compare text the right way.
 */
public class StatusChecks {

    /** Is the status exactly "APPROVED"? */
    static boolean isApproved(String status) {
        // TODO: compare the status with "APPROVED"
        return false;
    }

    /** A status is final if it is "APPROVED" or "DECLINED" (anything else, like "PENDING", can still change). */
    static boolean isFinal(String status) {
        // TODO: check for either final status
        return false;
    }

    /** Are the two currency codes the same, ignoring capitals? Example: ("eur", "EUR") -> true */
    static boolean isSameCurrency(String first, String second) {
        // TODO: compare the two currency codes, ignoring capitals
        return false;
    }
}
