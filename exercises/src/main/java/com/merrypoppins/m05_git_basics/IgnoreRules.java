package com.merrypoppins.m05_git_basics;

/**
 * Exercise 3: which files should Git never save? (That's what a .gitignore file is for.)
 */
public class IgnoreRules {

    /**
     * Should this file be ignored? Yes if ANY of these rules matches the path:
     *
     *   - it is inside the build folder:   starts with "target/"
     *   - it is an IntelliJ settings file: starts with ".idea/"
     *   - it is a log file:                ends with ".log"
     *   - it is a compiled Java file:      ends with ".class"
     *   - it is the secrets file:          is exactly ".env"
     *
     * Everything else (like source code) must NOT be ignored.
     * Example: "target/classes/App.class" -> true,  "src/main/java/App.java" -> false
     */
    static boolean shouldIgnore(String path) {
        // TODO: check the five rules
        return false;
    }
}
