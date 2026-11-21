package com.merrypoppins.m00_sample;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Smoke test: proves the toolchain works. Nothing for the learner to do here. */
class ToolchainTest {

    @Test
    void javaVersionIsAtLeast21() {
        int version = Runtime.version().feature();
        assertTrue(version >= 21, "Expected Java 21 or newer, but this is Java " + version);
    }
}
