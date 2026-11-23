package com.merrypoppins.m04_strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatusChecksTest {

    /**
     * Simulates text that arrived from an API response: the same letters, but a new String object.
     * (Text written directly in the code can hide the == bug; text from outside does not.)
     */
    private static String fromApi(String text) {
        return new String(text);
    }

    @Test
    void approvedStatusFromAnApiIsApproved() {
        assertEquals(true, StatusChecks.isApproved(fromApi("APPROVED")),
                "isApproved should be true for the status APPROVED that came back from the API");
    }

    @Test
    void pendingIsNotApproved() {
        assertEquals(false, StatusChecks.isApproved(fromApi("PENDING")),
                "isApproved should be false for the status PENDING");
    }

    @Test
    void approvedAndDeclinedAreFinal() {
        assertEquals(true, StatusChecks.isFinal(fromApi("APPROVED")),
                "isFinal should be true for the status APPROVED that came back from the API");
        assertEquals(true, StatusChecks.isFinal(fromApi("DECLINED")),
                "isFinal should be true for the status DECLINED that came back from the API");
    }

    @Test
    void pendingIsNotFinal() {
        assertEquals(false, StatusChecks.isFinal(fromApi("PENDING")),
                "isFinal should be false for the status PENDING: it can still change");
    }

    @Test
    void sameCurrencyFromAnApi() {
        assertEquals(true, StatusChecks.isSameCurrency(fromApi("EUR"), fromApi("EUR")),
                "isSameCurrency should be true for EUR and EUR that came back from the API");
    }

    @Test
    void sameCurrencyIgnoringCapitals() {
        assertEquals(true, StatusChecks.isSameCurrency("eur", "EUR"),
                "isSameCurrency(\"eur\", \"EUR\") should be true: capitals don't matter");
    }

    @Test
    void differentCurrencies() {
        assertEquals(false, StatusChecks.isSameCurrency("EUR", "USD"),
                "isSameCurrency(\"EUR\", \"USD\") should be false");
    }
}
