package com.merrypoppins.m01_first_program;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Test helper (not a test): runs a program and collects the lines it printed.
 * Trailing spaces are ignored; everything else (capitals, spaces, punctuation) counts.
 */
final class ConsoleCapture {

    private ConsoleCapture() {
    }

    static List<String> linesPrintedBy(Runnable program) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            program.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8)
                .lines()
                .map(String::stripTrailing)
                .toList();
    }

    /** Line number {@code n} (1-based), or a readable failure if the program printed fewer lines. */
    static String line(List<String> lines, int n, String expected, String what) {
        if (lines.size() < n) {
            throw new AssertionError("Line " + n + " (" + what + ") is missing: the program printed only "
                    + lines.size() + " line(s). Expected line " + n + " to be: " + expected);
        }
        return lines.get(n - 1);
    }
}
