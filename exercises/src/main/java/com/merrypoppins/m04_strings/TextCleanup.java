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
        String currency = input.toUpperCase();
        String result = currency.trim();
        return result;
    }

    /**
     * A Ukrainian IBAN starts with "UA" and is exactly 29 characters long.
     *
     * Example: "UA213223130000026007233566001"  ->  true
     */
    static boolean isUkrainianIban(String iban) {
        boolean ukrainian = iban.startsWith("UA");
        int number = iban.length();
        boolean result = ukrainian && number == 29;
        return result;
    }

    /**
     * Does the payment description mention a refund? The word can be written in any
     * capitals ("refund", "Refund", "REFUND") and can be anywhere in the text.
     *
     * Example: "Partial refund: broken cup"  ->  true
     */
    static boolean mentionsRefund(String description) {
        String a = "refund";
        boolean refund = description.toLowerCase().contains(a);
        return refund;
    }
}
