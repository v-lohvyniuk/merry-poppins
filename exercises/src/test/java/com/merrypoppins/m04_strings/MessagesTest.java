package com.merrypoppins.m04_strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MessagesTest {

    @Test
    void greetingUsesTheFirstName() {
        assertEquals("Hello, Anna!", Messages.greeting("Anna"),
                "greeting(\"Anna\") should match exactly, including the comma and the '!'");
    }

    @Test
    void greetingForAnotherName() {
        assertEquals("Hello, Oleh!", Messages.greeting("Oleh"),
                "greeting(\"Oleh\") should use the name it was given");
    }

    @Test
    void transferLineShowsAmountCurrencyAndRecipient() {
        assertEquals("Sent 25 EUR to Anna", Messages.transferLine("Anna", 25, "EUR"),
                "transferLine(\"Anna\", 25, \"EUR\") should match exactly, including the spaces");
    }

    @Test
    void transferLineForAnotherTransfer() {
        assertEquals("Sent 4 UAH to Coffee Corner", Messages.transferLine("Coffee Corner", 4, "UAH"),
                "transferLine(\"Coffee Corner\", 4, \"UAH\") should use the values it was given");
    }

    @Test
    void totalLineAddsTheTwoAmounts() {
        assertEquals("Total: 5 EUR", Messages.totalLine(2, 3),
                "totalLine(2, 3) should show the SUM, 2 + 3 = 5");
    }

    @Test
    void totalLineForOtherAmounts() {
        assertEquals("Total: 100 EUR", Messages.totalLine(40, 60),
                "totalLine(40, 60) should show the SUM, 40 + 60 = 100");
    }
}
