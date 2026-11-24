package com.merrypoppins.m05_git_basics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatusLinesTest {

    @Test
    void questionMarksMeanUntracked() {
        assertEquals(true, StatusLines.isUntracked("?? notes.txt"),
                "isUntracked(\"?? notes.txt\") should be true: ?? means Git has never saved this file");
    }

    @Test
    void changedButNotStagedIsNotUntracked() {
        assertEquals(false, StatusLines.isUntracked(" M Wallet.java"),
                "isUntracked(\" M Wallet.java\") should be false: Git knows this file, it was only changed");
    }

    @Test
    void mInFirstColumnMeansStaged() {
        assertEquals(true, StatusLines.isStaged("M  Wallet.java"),
                "isStaged(\"M  Wallet.java\") should be true: M as the FIRST letter means staged");
    }

    @Test
    void newFileAddedIsStaged() {
        assertEquals(true, StatusLines.isStaged("A  PushCheck.java"),
                "isStaged(\"A  PushCheck.java\") should be true: A as the first letter means a new file was staged");
    }

    @Test
    void changedButNotStagedIsNotStaged() {
        assertEquals(false, StatusLines.isStaged(" M Wallet.java"),
                "isStaged(\" M Wallet.java\") should be false: the first letter is a space, so nothing is staged");
    }

    @Test
    void untrackedFileWhoseNameStartsWithMIsNotStaged() {
        assertEquals(false, StatusLines.isStaged("?? Main.java"),
                "isStaged(\"?? Main.java\") should be false: the M is in the file NAME, not in the 2-letter code");
    }

    @Test
    void mInSecondColumnMeansUnstagedChanges() {
        assertEquals(true, StatusLines.hasUnstagedChanges(" M Wallet.java"),
                "hasUnstagedChanges(\" M Wallet.java\") should be true: M as the SECOND letter means changed but not staged");
    }

    @Test
    void stagedThenChangedAgainHasUnstagedChanges() {
        assertEquals(true, StatusLines.hasUnstagedChanges("MM Wallet.java"),
                "hasUnstagedChanges(\"MM Wallet.java\") should be true: staged, then changed again after git add");
    }

    @Test
    void newFileChangedAgainHasUnstagedChanges() {
        assertEquals(true, StatusLines.hasUnstagedChanges("AM PushCheck.java"),
                "hasUnstagedChanges(\"AM PushCheck.java\") should be true: a new staged file, changed again");
    }

    @Test
    void onlyStagedHasNoUnstagedChanges() {
        assertEquals(false, StatusLines.hasUnstagedChanges("M  Wallet.java"),
                "hasUnstagedChanges(\"M  Wallet.java\") should be false: everything is staged, the second letter is a space");
    }
}
