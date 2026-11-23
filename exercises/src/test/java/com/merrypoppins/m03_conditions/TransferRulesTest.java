package com.merrypoppins.m03_conditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TransferRulesTest {

    @Test
    void enoughMoneyWhenBalanceIsBigger() {
        assertEquals(true, TransferRules.hasEnoughMoney(5000, 1000),
                "hasEnoughMoney(balance 5000, amount 1000) should be true");
    }

    @Test
    void enoughMoneyWhenBalanceIsExactlyTheAmount() {
        assertEquals(true, TransferRules.hasEnoughMoney(1000, 1000),
                "hasEnoughMoney(balance 1000, amount 1000) should be true: exactly enough is enough");
    }

    @Test
    void notEnoughMoneyWhenOneCentShort() {
        assertEquals(false, TransferRules.hasEnoughMoney(999, 1000),
                "hasEnoughMoney(balance 999, amount 1000) should be false: one cent short");
    }

    @Test
    void withinLimitWhenBelowTheLimit() {
        assertEquals(true, TransferRules.isWithinDailyLimit(1000, 50000),
                "isWithinDailyLimit(amount 1000, limit 50000) should be true");
    }

    @Test
    void withinLimitWhenExactlyTheLimit() {
        assertEquals(true, TransferRules.isWithinDailyLimit(50000, 50000),
                "isWithinDailyLimit(amount 50000, limit 50000) should be true: exactly the limit is OK");
    }

    @Test
    void overLimitWhenOneCentMore() {
        assertEquals(false, TransferRules.isWithinDailyLimit(50001, 50000),
                "isWithinDailyLimit(amount 50001, limit 50000) should be false: one cent over the limit");
    }

    @Test
    void canTransferWhenAllRulesPass() {
        assertEquals(true, TransferRules.canTransfer(5000, 1000, 50000, false),
                "canTransfer(balance 5000, amount 1000, limit 50000, card blocked false) should be true");
    }

    @Test
    void canTransferWithExactlyEnoughMoneyAtExactlyTheLimit() {
        assertEquals(true, TransferRules.canTransfer(50000, 50000, 50000, false),
                "canTransfer(balance 50000, amount 50000, limit 50000, card blocked false) should be true: "
                        + "exactly enough money and exactly the limit are both OK");
    }

    @Test
    void cannotTransferWhenCardIsBlocked() {
        assertEquals(false, TransferRules.canTransfer(5000, 1000, 50000, true),
                "canTransfer(balance 5000, amount 1000, limit 50000, card blocked true) should be false: the card is blocked");
    }

    @Test
    void cannotTransferWithoutEnoughMoney() {
        assertEquals(false, TransferRules.canTransfer(500, 1000, 50000, false),
                "canTransfer(balance 500, amount 1000, limit 50000, card blocked false) should be false: not enough money");
    }

    @Test
    void cannotTransferOverTheDailyLimit() {
        assertEquals(false, TransferRules.canTransfer(100000, 60000, 50000, false),
                "canTransfer(balance 100000, amount 60000, limit 50000, card blocked false) should be false: over the daily limit");
    }
}
