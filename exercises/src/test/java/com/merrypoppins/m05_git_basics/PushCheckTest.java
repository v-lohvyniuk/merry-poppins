package com.merrypoppins.m05_git_basics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PushCheckTest {

    @Test
    void pushWhenAheadAndUpToDate() {
        assertEquals("PUSH", PushCheck.pushAdvice(2, 0, false),
                "pushAdvice(ahead 2, behind 0, uncommitted false) should be PUSH");
    }

    @Test
    void nothingToPushWhenNotAhead() {
        assertEquals("NOTHING_TO_PUSH", PushCheck.pushAdvice(0, 0, false),
                "pushAdvice(ahead 0, behind 0, uncommitted false) should be NOTHING_TO_PUSH");
    }

    @Test
    void pullFirstWhenBehind() {
        assertEquals("PULL_FIRST", PushCheck.pushAdvice(2, 3, false),
                "pushAdvice(ahead 2, behind 3, uncommitted false) should be PULL_FIRST: GitHub has commits you don't have");
    }

    @Test
    void pullFirstEvenWithNothingToPush() {
        assertEquals("PULL_FIRST", PushCheck.pushAdvice(0, 1, false),
                "pushAdvice(ahead 0, behind 1, uncommitted false) should be PULL_FIRST: being behind is checked before 'nothing to push'");
    }

    @Test
    void commitFirstBeatsEverythingElse() {
        assertEquals("COMMIT_FIRST", PushCheck.pushAdvice(2, 3, true),
                "pushAdvice(ahead 2, behind 3, uncommitted true) should be COMMIT_FIRST: uncommitted changes are checked first");
    }

    @Test
    void canPushNowWhenEverythingIsReady() {
        assertEquals(true, PushCheck.canPushNow(2, 0, false),
                "canPushNow(ahead 2, behind 0, uncommitted false) should be true");
    }

    @Test
    void cannotPushWithUncommittedChanges() {
        assertEquals(false, PushCheck.canPushNow(2, 0, true),
                "canPushNow(ahead 2, behind 0, uncommitted true) should be false: commit first");
    }

    @Test
    void cannotPushWhenBehindEvenIfMoreAhead() {
        assertEquals(false, PushCheck.canPushNow(2, 1, false),
                "canPushNow(ahead 2, behind 1, uncommitted false) should be false: behind by 1, so pull first");
    }

    @Test
    void cannotPushWithNothingAhead() {
        assertEquals(false, PushCheck.canPushNow(0, 0, false),
                "canPushNow(ahead 0, behind 0, uncommitted false) should be false: there is nothing to push");
    }
}
