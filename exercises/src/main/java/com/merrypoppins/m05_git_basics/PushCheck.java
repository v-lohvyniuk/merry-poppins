package com.merrypoppins.m05_git_basics;

/**
 * Exercise 4: before you push, check the situation, like "git status" tells you.
 *
 *   ahead  = your commits that GitHub doesn't have yet
 *   behind = commits on GitHub that you don't have yet
 *   hasUncommittedChanges = you changed files but didn't commit them
 */
public class PushCheck {

    /**
     * What should you do next? Check IN THIS ORDER and hand back the first that applies:
     *
     *   1. there are uncommitted changes   ->  "COMMIT_FIRST"
     *   2. you are behind GitHub           ->  "PULL_FIRST"
     *   3. you are not ahead (nothing new) ->  "NOTHING_TO_PUSH"
     *   otherwise                          ->  "PUSH"
     */
    static String pushAdvice(int ahead, int behind, boolean hasUncommittedChanges) {
        // TODO: use if / else if / else
        return "";
    }

    /**
     * Can you push right now? Only if ALL are true:
     * you are ahead (at least 1 commit), you are not behind at all, and there are NO uncommitted changes.
     */
    static boolean canPushNow(int ahead, int behind, boolean hasUncommittedChanges) {
        // TODO: combine the three rules
        return false;
    }
}
