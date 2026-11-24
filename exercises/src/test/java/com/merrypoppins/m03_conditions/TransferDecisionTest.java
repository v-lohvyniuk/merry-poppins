package com.merrypoppins.m03_conditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TransferDecisionTest {

    @Test
    void approvedWhenThereIsEnoughMoney() {
        assertEquals("APPROVED", TransferDecision.decide(5000, 1000),
                "decide(balance 5000, amount 1000) should be APPROVED");
    }

    @Test
    void approvedWhenBalanceIsExactlyTheAmount() {
        assertEquals("APPROVED", TransferDecision.decide(1000, 1000),
                "decide(balance 1000, amount 1000) should be APPROVED: exactly enough is enough");
    }

    @Test
    void declinedWhenOneCentShort() {
        assertEquals("DECLINED", TransferDecision.decide(999, 1000),
                "decide(balance 999, amount 1000) should be DECLINED: one cent short");
    }

    @Test
    void reasonIsNoneWhenEverythingIsFine() {
        assertEquals("NONE", TransferDecision.declineReason(5000, 1000, 50000, false),
                "declineReason(balance 5000, amount 1000, limit 50000, card blocked false) should be NONE: every rule passes");
    }

    @Test
    void reasonIsCardBlocked() {
        assertEquals("CARD_BLOCKED", TransferDecision.declineReason(5000, 1000, 50000, true),
                "declineReason(balance 5000, amount 1000, limit 50000, card blocked true) should be CARD_BLOCKED");
    }

    @Test
    void reasonIsOverDailyLimit() {
        assertEquals("OVER_DAILY_LIMIT", TransferDecision.declineReason(100000, 60000, 50000, false),
                "declineReason(balance 100000, amount 60000, limit 50000, card blocked false) should be OVER_DAILY_LIMIT");
    }

    @Test
    void reasonIsInsufficientFunds() {
        assertEquals("INSUFFICIENT_FUNDS", TransferDecision.declineReason(500, 1000, 50000, false),
                "declineReason(balance 500, amount 1000, limit 50000, card blocked false) should be INSUFFICIENT_FUNDS");
    }

    @Test
    void cardBlockedIsReportedFirstEvenIfEverythingElseFails() {
        assertEquals("CARD_BLOCKED", TransferDecision.declineReason(500, 60000, 50000, true),
                "declineReason(balance 500, amount 60000, limit 50000, card blocked true): all three rules fail, "
                        + "and the blocked card is checked first, so it should be CARD_BLOCKED");
    }

    @Test
    void overLimitIsReportedBeforeInsufficientFunds() {
        assertEquals("OVER_DAILY_LIMIT", TransferDecision.declineReason(500, 60000, 50000, false),
                "declineReason(balance 500, amount 60000, limit 50000, card blocked false): over the limit AND not enough money, "
                        + "and the limit is checked before the money, so it should be OVER_DAILY_LIMIT");
    }
}
