package com.merrypoppins.m01_first_program;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReceiptTest {

    private List<String> run() {
        return ConsoleCapture.linesPrintedBy(() -> Receipt.main(new String[0]));
    }

    private void assertLine(int n, String expected, String what) {
        String actual = ConsoleCapture.line(run(), n, expected, what);
        assertEquals(expected, actual,
                "Line " + n + " of the receipt (" + what + ") should match exactly, "
                        + "including capital letters, spaces and punctuation");
    }

    @Test
    void firstLineIsTheTitle() {
        assertLine(1, "MerryBank payment receipt", "the title");
    }

    @Test
    void secondLineIsTheRecipient() {
        assertLine(2, "To: Coffee Corner", "who the money goes to");
    }

    @Test
    void thirdLineIsTheAmount() {
        assertLine(3, "Amount: 4.50 EUR", "the amount");
    }

    @Test
    void fourthLineIsTheStatus() {
        assertLine(4, "Status: APPROVED", "the status");
    }

    @Test
    void printsExactlyFourLines() {
        assertEquals(4, run().size(),
                "The receipt should have exactly 4 lines: title, recipient, amount, status");
    }
}
