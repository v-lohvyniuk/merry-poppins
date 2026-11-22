# Learning progress

**Current module:** m02_variables_numbers
**Current step:** m02 concepts 1–2 of 4 taught; Wallet exercise not started
**Next session starts with:** quiz, then the Wallet exercise (re-assignment), then concept 3 (`int` maths, `/` and `%`)
**Sessions so far:** 2

---

## For reviewer
<!-- The tutor adds items here for the lead to look at. Newest first.
     Format: - [ ] YYYY-MM-DD · module · what to look at · why (file/line if relevant)
     The reviewer ticks [x] or deletes items once handled. -->
- [ ] 2026-11-22 · tooling · the session_clock hook is unreliable: it didn't start a new session when she returned after the last wrap-up (the clock showed ~1360 min), then reset to a "new session" at 21:58 in the middle of the conversation. Session 2 really started ~21:23, so .session.json started_at (21:58) is too late. Durations in the log are the tutor's estimates.
- [ ] 2026-11-21 · m01_first_program · check.sh gives BUILD PROBLEM: "release version 21 not supported". Maven is running on a JDK older than 21, so no tests can run. /Library/Java/JavaVirtualMachines has jdk-17, temurin-21 and jdk-24, so 21 is installed, but Maven probably picks up 17 (JAVA_HOME?). Please check the JDK / JAVA_HOME setup. UPDATE 22:40: after a session restart the build works (tests run), so this looks resolved. Just confirm the setup is now permanent.
- [ ] 2026-11-21 · m00_sample · progress.md pointed to m00_sample, which has no curriculum file · tutor switched current module to m01_first_program (first file in curriculum/). Please confirm.

## Struggles / revisit
<!-- One row per topic. Update the row instead of adding duplicates.
     Status: open → improving → solid (remove rows that have been solid for 2+ weeks). -->
| Since | Module | Topic | What goes wrong | Cards | Status |
|---|---|---|---|---|---|
| 2026-11-22 | m02_variables_numbers | re-assignment: "current" vs "initial" value | first teach-back said "initial balance" (same as the diagnostic mistake); got it right after one correction | m02-002 | open |

---

## Session log (newest first)
<!-- /wrap-up inserts each new session directly BELOW this line. Do not reorder. -->
### Session 2 · 2026-11-22 · ~50 min (estimate; the clock reset mid-session) · m01_first_program → m02_variables_numbers
- **Covered:** m01 concept 3 (`;` and comments), m01 concept 4 (reading compiler errors), Receipt exercise, "break it on purpose" step; m02 concept 1 (variables), m02 concept 2 (re-assignment)
- **Teach-back:** `;`/comments: first try ✅ · compiler errors: after correction 🔁 (missed the line number and "fix the first error first") · variables: first try ✅ · re-assignment: after correction 🔁 (said "initial" instead of "current")
- **Quiz:** 2/2 correct
- **Exercise:** m01 PASS (7/7, no test files changed, cleaned up leftover TODOs after review; break-it step done: read file, line and "';' expected" correctly) · highest hint level: 0 · m02 Wallet not started
- **Mood / energy:** focused and quick; wanted to keep going past 40 min
- **Next:** quiz, then the Wallet exercise (re-assignment), then concept 3 (`int` maths, `/` and `%`)
### Session 1 · 2026-11-21 · ~105 min on the clock (app was left open; active time ~30–40 min) · m01_first_program
- **Covered:** concept 1 (`main` runs top to bottom), concept 2 (`println`, exact text, one line each); started the Greeting exercise
- **Teach-back:** main: first try ✅ · println: first try ✅ (also spotted on her own that `"milk "` ≠ `Milk`)
- **Quiz:** no cards due
- **Exercise:** in progress: Greeting tests pass (3/7 passed in total); the 4 Receipt tests fail because Receipt isn't started yet · highest hint level: 0
- **Mood / energy:** focused, confident; a Java/Maven setup problem interrupted the session
- **Next:** quiz (2 cards), then concept 3 (`;` and comments), then the Receipt exercise
