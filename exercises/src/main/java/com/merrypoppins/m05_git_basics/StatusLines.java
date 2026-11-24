package com.merrypoppins.m05_git_basics;

/**
 * Exercise 1: read one line of "git status --short", like a tool would.
 *
 * Each line starts with a 2-letter code, then a space, then the file name:
 *
 *   "?? notes.txt"       untracked: Git has never saved this file
 *   " M Wallet.java"     changed, but NOT staged (not added yet)
 *   "M  Wallet.java"     changed and staged (added, ready to commit)
 *   "A  PushCheck.java"  a new file, staged
 *   "MM Wallet.java"     staged, then changed AGAIN after git add
 *   "AM PushCheck.java"  a new file, staged, then changed again
 *
 * The first letter is about the staging area, the second letter about your working folder.
 * Each method returns false for now. Replace false with the right check.
 */
public class StatusLines {

    /** Is the file untracked? (The line starts with "??") */
    static boolean isUntracked(String line) {
        // TODO
        return false;
    }

    /** Is something staged for this file? (The FIRST letter is M or A) */
    static boolean isStaged(String line) {
        // TODO
        return false;
    }

    /** Does the file have changes that are NOT staged? (The SECOND letter is M: " M", "MM" or "AM") */
    static boolean hasUnstagedChanges(String line) {
        // TODO
        return false;
    }
}
