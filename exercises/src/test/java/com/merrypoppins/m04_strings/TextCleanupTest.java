package com.merrypoppins.m04_strings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TextCleanupTest {

    @Test
    void currencyIsUpperCased() {
        assertEquals("USD", TextCleanup.normalizeCurrency("usd"),
                "normalizeCurrency(\"usd\") should be all capital letters");
    }

    @Test
    void currencyLosesSpacesAroundIt() {
        assertEquals("EUR", TextCleanup.normalizeCurrency("  eur "),
                "normalizeCurrency(\"  eur \") should have no spaces around it and be all capitals");
    }

    @Test
    void validUkrainianIban() {
        assertEquals(true, TextCleanup.isUkrainianIban("UA213223130000026007233566001"),
                "isUkrainianIban(\"UA213223130000026007233566001\") should be true: starts with UA and is 29 characters");
    }

    @Test
    void ibanFromAnotherCountryIsNotUkrainian() {
        assertEquals(false, TextCleanup.isUkrainianIban("PL61109010140000071219812874"),
                "isUkrainianIban(\"PL61109010140000071219812874\") should be false: it starts with PL, not UA");
    }

    @Test
    void ukrainianIbanThatIsTooShort() {
        assertEquals(false, TextCleanup.isUkrainianIban("UA21322313000002600723356600"),
                "isUkrainianIban(\"UA21322313000002600723356600\") should be false: it is 28 characters, not 29");
    }

    @Test
    void refundIsFoundInAnyCase() {
        assertEquals(true, TextCleanup.mentionsRefund("REFUND for order 1042"),
                "mentionsRefund(\"REFUND for order 1042\") should be true: capitals don't matter");
    }

    @Test
    void refundIsFoundInTheMiddle() {
        assertEquals(true, TextCleanup.mentionsRefund("Partial refund: broken cup"),
                "mentionsRefund(\"Partial refund: broken cup\") should be true: the word can be anywhere");
    }

    @Test
    void normalPaymentDoesNotMentionRefund() {
        assertEquals(false, TextCleanup.mentionsRefund("Payment to Coffee Corner"),
                "mentionsRefund(\"Payment to Coffee Corner\") should be false");
    }
}
