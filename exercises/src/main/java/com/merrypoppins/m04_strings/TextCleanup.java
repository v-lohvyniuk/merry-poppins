package com.merrypoppins.m04_strings;

/**
 * Exercise 2: cleaning and checking text that users type in.
 *
 * Use the String methods from the lesson (length, toUpperCase, toLowerCase, trim, startsWith, contains).
 */
public class TextCleanup {

    /**
     * Users type currency codes in many ways. Turn it into the standard form:
     * no spaces around it, all capital letters.
     *
     * Examples: "usd" -> "USD",  "  eur " -> "EUR"
     */
    static String normalizeCurrency(String input) {
        // TODO: clean up the input
        return "";
    }

    /**
     * A Ukrainian IBAN starts with "UA" and is exactly 29 characters long.
     *
     * Example: "UA213223130000026007233566001"  ->  true
     */
    static boolean isUkrainianIban(String iban) {
        // TODO: check both rules
        return false;
    }

    /**
     * Does the payment description mention a refund? The word can be written in any
     * capitals ("refund", "Refund", "REFUND") and can be anywhere in the text.
     *
     * Example: "Partial refund: broken cup"  ->  true
     */
    static boolean mentionsRefund(String description) {
        // TODO: look for the word "refund"
        return false;
    }
}
