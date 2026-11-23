package com.merrypoppins.m02_variables_numbers;

/**
 * Exercise 1: a wallet balance that changes during the day.
 *
 * The test hands in four numbers (the names in brackets) and checks the number you hand back.
 * You only write the lines marked TODO. The rest is given for now.
 */
public class Wallet {

    /**
     * The balance at the end of the day.
     *
     * The customer starts with startBalance, pays for a coffee, then pays for groceries,
     * then receives a salary. All amounts are whole euros.
     *
     * Example: start 100, coffee 4, groceries 30, salary 50  ->  116
     */
    static int balanceAfterDay(int startBalance, int coffee, int groceries, int salary) {
        int balance = startBalance;
        balance = balance - coffee; 
        balance = balance - groceries;
        balance = balance + salary; 
        return balance;
    }
}
