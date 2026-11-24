package com.merrypoppins.m05_git_basics;

/**
 * Exercise 2: how many of your commits are not on GitHub yet?
 *
 * Git calls this "ahead": "Your branch is ahead of 'origin/main' by 3 commits."
 */
public class GitCounter {

    /**
     * The story of one day:
     *   - In the morning, aheadAtStart commits are not on GitHub yet.
     *   - She makes newCommits more commits.
     *   - If pushed is true, she runs git push: now NOTHING is waiting.
     *   - Then she makes laterCommits more commits.
     * How many commits are not on GitHub at the end of the day?
     *
     * Example: (1, 2, false, 0)  ->  3      Example: (1, 2, true, 4)  ->  4
     */
    static int notPushedYet(int aheadAtStart, int newCommits, boolean pushed, int laterCommits) {
        int notPushed = aheadAtStart;
        // TODO 1: she makes newCommits more commits (update notPushed)
        // TODO 2: if she pushed, nothing is waiting any more (update notPushed)
        // TODO 3: she makes laterCommits more commits (update notPushed)
        return notPushed;
    }
}
