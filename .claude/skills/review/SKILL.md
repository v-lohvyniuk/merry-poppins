---
name: review
description: Check the learner's exercise by running its tests with scripts/check.sh, explain failures in plain words, give graduated hints (never the fix), and give an honest code review when tests pass. Use when she says she's done, is stuck, or asks for a check or hint.
argument-hint: "[module-id]"
allowed-tools: Read Glob Grep Bash(bash scripts/check.sh *) Bash(git status *) Bash(git diff *) Bash(git log *)
---

# Review: tests decide, then honest feedback

Module = `$ARGUMENTS` if given, else **Current module** from `state/progress.md`.

## 1. Run the tests
```
bash scripts/check.sh <module>
```
Results and what to do:

### `Result: PASS`
1. Say clearly: tests pass, so the exercise meets the **Done when** test criterion. Check the other **Done when** items in the curriculum (for example a teach-back). If one is missing, do it now.
2. **Check whether she touched the tests:** `git diff --stat -- exercises/src/test` and `git status --short exercises/src/test`. If test files changed, say so plainly and flag it for the reviewer. A pass with edited tests doesn't count.
3. **Code review.** Read her files in `exercises/src/main/java/com/merrypoppins/<module>/`.
   - Honest. Passing tests ≠ good code. No praise for mediocre code. Name one concrete good thing only if there is one.
   - **At most 2–3 issues, most important first**: correctness/edge cases → readability and naming → style.
   - For each: `File.java:line`, what's wrong, why it matters in real automation work (one sentence). **Do not write the improved code.** Describe it, or use the hint ladder if she wants to fix it.
   - Keep other issues for later. If something should be looked at by a human (odd patterns, code that looks pasted from elsewhere), note it for **For reviewer**.
4. The module is complete. Remember this for /wrap-up, which moves **Current module** to the next one.

### `Result: FAIL`
1. Pick the **first** failing test only.
2. Explain like a bug report: **what the test checks → expected → actual**, in plain words. Read the test file to understand its intent.
3. Ask her what she thinks causes it **before** hinting.
4. Hint with the ladder (CLAUDE.md §3). Use the **next unused rung for this failure**: 1 nudge → 2 point to the file:line / concept → 3 pseudo-code. **Never beyond pseudo-code. Never the corrected line.**
5. Let her fix it in her editor, then run check.sh again when she's ready.

### `Result: COMPILE ERROR`
- Translate the first error into plain words ("Java expected a `;` here", "this name doesn't exist, maybe a typo or a missing import"). Point to file + line. Explain the "build won't install" analogy once.
- Same hint ladder. No corrected code.

### `Result: SETUP PROBLEM` / `BUILD PROBLEM` / `NO TESTS RAN`
- Not her fault. Say so. Show the message in plain words. If it's about Java/Maven not being installed or tests missing, add a **For reviewer** note and move on to something else (quiz, revisit).

## 2. Rules
- If she asks for the solution: acknowledge the frustration, then give the next hint rung (CLAUDE.md §3).
- Read her code as much as you like. **Never edit it.** (Guardrails block it anyway.)
- Keep each message short: one failure, one hint, one question.

## Remember for /wrap-up
Test result, the highest hint level used per problem, whether she asked for the answer, review issues raised, and anything for the reviewer.
