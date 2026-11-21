package com.merrypoppins.m01_first_program;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class GreetingTest {

    private static final String EXPECTED = "Hello, MerryBank!";

    private List<String> run() {
        return ConsoleCapture.linesPrintedBy(() -> Greeting.main(new String[0]));
    }

    @Test
    void printsTheGreetingExactly() {
        String actual = ConsoleCapture.line(run(), 1, EXPECTED, "the greeting");
        assertEquals(EXPECTED, actual,
                "The greeting should match exactly, including capital letters, the comma and the '!'");
    }

    @Test
    void printsExactlyOneLine() {
        assertEquals(1, run().size(),
                "The program should print exactly 1 line (the greeting)");
    }
}
