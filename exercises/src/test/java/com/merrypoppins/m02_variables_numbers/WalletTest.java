package com.merrypoppins.m02_variables_numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WalletTest {

    private void check(int start, int coffee, int groceries, int salary, int expected, String why) {
        int actual = Wallet.balanceAfterDay(start, coffee, groceries, salary);
        assertEquals(expected, actual,
                "Start " + start + ", coffee " + coffee + ", groceries " + groceries + ", salary " + salary
                        + ": the balance at the end of the day should be " + expected + " (" + why + ")");
    }

    @Test
    void coffeeIsTakenFromTheBalance() {
        check(100, 4, 0, 0, 96, "100 - 4");
    }

    @Test
    void groceriesAreTakenFromTheBalance() {
        check(100, 0, 30, 0, 70, "100 - 30");
    }

    @Test
    void salaryIsAddedToTheBalance() {
        check(100, 0, 0, 50, 150, "100 + 50");
    }

    @Test
    void fullDayUsesTheCurrentBalanceEachTime() {
        check(100, 4, 30, 50, 116, "100 - 4 = 96, then 96 - 30 = 66, then 66 + 50 = 116");
    }

    @Test
    void fullDayForAnotherCustomer() {
        check(2500, 3, 120, 0, 2377, "2500 - 3 - 120 + 0");
    }
}
