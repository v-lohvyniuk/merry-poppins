package com.merrypoppins.m05_git_basics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GitCounterTest {

    private void check(int aheadAtStart, int newCommits, boolean pushed, int laterCommits, int expected, String why) {
        assertEquals(expected, GitCounter.notPushedYet(aheadAtStart, newCommits, pushed, laterCommits),
                "notPushedYet(ahead at start " + aheadAtStart + ", new commits " + newCommits + ", pushed " + pushed
                        + ", later commits " + laterCommits + ") should be " + expected + ": " + why);
    }

    @Test
    void newCommitsAreAdded() {
        check(1, 2, false, 0, 3, "1 waiting + 2 new = 3, no push");
    }

    @Test
    void pushingClearsTheCounter() {
        check(1, 2, true, 0, 0, "after the push nothing is waiting");
    }

    @Test
    void commitsAfterThePushCount() {
        check(1, 2, true, 4, 4, "the push cleared everything, then 4 new commits");
    }

    @Test
    void laterCommitsWithoutPushAddUp() {
        check(0, 3, false, 1, 4, "0 + 3 + 1, no push");
    }

    @Test
    void anotherDay() {
        check(5, 0, true, 2, 2, "the push cleared the 5, then 2 new commits");
    }
}
