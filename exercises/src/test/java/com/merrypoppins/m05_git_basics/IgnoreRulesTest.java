package com.merrypoppins.m05_git_basics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IgnoreRulesTest {

    private void check(String path, boolean expected, String why) {
        assertEquals(expected, IgnoreRules.shouldIgnore(path),
                "shouldIgnore(\"" + path + "\") should be " + expected + ": " + why);
    }

    @Test
    void buildOutputIsIgnored() {
        check("target/classes/App.class", true, "it is inside the build folder target/");
    }

    @Test
    void ideaSettingsAreIgnored() {
        check(".idea/workspace.xml", true, "it is an IntelliJ settings file");
    }

    @Test
    void logFilesAreIgnored() {
        check("logs/test-run.log", true, "it ends with .log");
    }

    @Test
    void compiledClassFilesAreIgnoredAnywhere() {
        check("out/App.class", true, "it ends with .class, even outside target/");
    }

    @Test
    void secretsFileIsIgnored() {
        check(".env", true, "it is exactly the secrets file .env");
    }

    @Test
    void sourceCodeIsNotIgnored() {
        check("src/main/java/App.java", false, "source code must be saved");
    }

    @Test
    void exampleEnvFileIsNotIgnored() {
        check(".env.example", false, "only the exact name .env is secret; the example file is meant to be shared");
    }

    @Test
    void targetInTheMiddleOfANameIsNotIgnored() {
        check("docs/target-audience.md", false, "it is not inside the target/ folder, 'target' is just part of the name");
    }
}
