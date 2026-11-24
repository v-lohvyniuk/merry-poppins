# Learning progress

**Current module:** m03_conditions
**Current step:** concepts 1–2 of 4 taught; TransferRules.java done (all its tests green, logic correct); TransferDecision and FeeTiers not started (need concepts 3–4)
**Next session starts with:** quiz (incl. m02-007 `=` direction, m03-001/002), a 2-min `!` flip revisit, then concept 3 (`if / else`) and TransferDecision.decide
**Sessions so far:** 4

---

## For reviewer
<!-- The tutor adds items here for the lead to look at. Newest first.
     Format: - [ ] YYYY-MM-DD · module · what to look at · why (file/line if relevant)
     The reviewer ticks [x] or deletes items once handled. -->
- [ ] 2026-11-24 · m03_conditions · test gap in TransferRulesTest: her canTransfer once had `enoughMoney = dailyLimitCents >= balanceCents` (limit vs balance, a non-rule) and `dailyLimit = balanceCents >= amountCents`, and ALL TransferRules tests passed. It wrongly declines a rich customer (balance 100000, amount 1000, limit 50000 → false). Tutor caught it in review and she fixed it; consider adding that test case.
- [ ] 2026-11-24 · process · session length: she asked for 90-min sessions. The m03 curriculum also says "90-minute sessions", but CLAUDE.md says 30–45 min. Please align. Today ran ~286 min on the clock incl. a ~2.5 h break (active ~140 min, estimate); she chose to continue every time a stop was offered.
- [ ] 2026-11-24 · m03_conditions · `!` (NOT) failed teach-back 3 times (said "NOR", then names only, then "none"). She then used `!cardBlocked` correctly on her own in canTransfer. canTransfer needed hint level 3 (pseudo-code "enough AND within limit AND NOT blocked").
- [ ] 2026-11-24 · m02/m03 · `=` direction still recurring: quiz card m02-007 wrong ("don't know" which box changes), predicted `apples` = 9 (thought `=` links boxes), answered `isFull = 3`, and wrote `cardBlocked = false` inside a condition. A human pairing moment on `=` vs `==` would help.
- [ ] 2026-11-24 · git · the merge with origin/main was finished (commit 960b28c; its message accidentally contains Git's `#` help lines). Push first failed with 403 for GitHub user ladybird1; it worked after she was added as a collaborator.
- [ ] 2026-11-23 · m03 · curriculum file missing: m02 is complete, but there is no curriculum/m03_*.md. The next session can only do quiz and revisits until it exists.
- [ ] 2026-11-23 · m02_variables_numbers · Wallet needed hint level 3 (pseudo-code). She tried new variables (startBalance1/2/3, first undeclared, then declared), then wrote `startBalance3 = balance;` (reversed direction of `=`) before getting to `balance = balance - ...`. The direction of `=` and "update the same box" look like the real gap. A human pairing moment on this may help. Final Wallet.java is correct and uses re-assignment.
- [ ] 2026-11-23 · tooling · session_clock again didn't start a new session when she returned (the clock continued from 21:58 on 2026-11-22). See the 2026-11-22 note.
- [ ] 2026-11-22 · tooling · the session_clock hook is unreliable: it didn't start a new session when she returned after the last wrap-up (the clock showed ~1360 min), then reset to a "new session" at 21:58 in the middle of the conversation. Session 2 really started ~21:23, so .session.json started_at (21:58) is too late. Durations in the log are the tutor's estimates.
- [ ] 2026-11-21 · m01_first_program · check.sh gives BUILD PROBLEM: "release version 21 not supported". Maven is running on a JDK older than 21, so no tests can run. /Library/Java/JavaVirtualMachines has jdk-17, temurin-21 and jdk-24, so 21 is installed, but Maven probably picks up 17 (JAVA_HOME?). Please check the JDK / JAVA_HOME setup. UPDATE 22:40: after a session restart the build works (tests run), so this looks resolved. Just confirm the setup is now permanent.
- [ ] 2026-11-21 · m00_sample · progress.md pointed to m00_sample, which has no curriculum file · tutor switched current module to m01_first_program (first file in curriculum/). Please confirm.

## Struggles / revisit
<!-- One row per topic. Update the row instead of adding duplicates.
     Status: open → improving → solid (remove rows that have been solid for 2+ weeks). -->
| Since | Module | Topic | What goes wrong | Cards | Status |
|---|---|---|---|---|---|
| 2026-11-22 | m02_variables_numbers | re-assignment: "current" vs "initial" value; direction of `=` | 11-22: first teach-back said "initial balance". 11-23: quiz card correct, but in Wallet she made new boxes instead of updating `balance` and wrote `x = balance` the wrong way round; needed hint level 3. Got it right in the end. 11-24: m02-007 wrong; thought `apples = pears` links the boxes; wrote `cardBlocked = false` in a condition | m02-002, m02-007, m03-001 | open |
| 2026-11-24 | m03_conditions | `!` (NOT) flips true/false | teach-back: "NOR", then "none"; 3 misses. In code she later wrote `!cardBlocked` correctly on her own | m03-002, m03-003 | improving |
| 2026-11-24 | m03_conditions | method computes the answer into a box but returns a fixed `true`/`false` | hasEnoughMoney returned `true`, then `false`, before she returned the box; renamed boxes instead of fixing their content once | m03-004 | open |

---

## Session log (newest first)
<!-- /wrap-up inserts each new session directly BELOW this line. Do not reorder. -->
### Session 4 · 2026-11-24 · ~286 min on the clock incl. a ~2.5 h break (active ~140 min, estimate) · m03_conditions
- **Covered:** finished the git merge + push; `=` revisit (copy, not link); m03 concept 1 (comparisons give a boolean, `=` vs `==`, boundary `<=`), concept 2 (`&&`, `||`, `!`); TransferRules exercise
- **Teach-back:** comparisons / `=` vs `==`: after correction 🔁 (first said "== for strings") · `&&`/`||`: after correction 🔁 · `!`: not yet ❌ (3 misses; later used correctly in code)
- **Quiz:** 6/7 correct (m02-007 `=` direction wrong)
- **Exercise:** in progress: TransferRules done (all its tests green + review bug fixed); TransferDecision and FeeTiers not started, 13/26 failing · highest hint level: 3 (canTransfer) · compile errors: typos (`balaceCents`, `frue`), `cardBlocked = false` in a condition, deleted box still used · review: green but wrong line 37 (limit vs balance) → she fixed it
- **Mood / energy:** motivated and persistent, chose to continue every time; short answers and typos near the end (tired)
- **Next:** quiz (incl. m02-007, m03-001/002), a 2-min `!` flip revisit, then concept 3 (`if / else`) and TransferDecision.decide
### Session 3 · 2026-11-23 · ~140 min on the clock with breaks (active ~80–90 min, estimate) · m02_variables_numbers
- **Covered:** Wallet exercise, concept 3 (`int` maths: `/` drops decimals, `%`, order matters), Fees exercise, concept 4 (`double`, `boolean`, lossy conversion), Cashback exercise
- **Teach-back:** int maths: first try ✅ (added: cut off, not rounded) · types: first try ✅
- **Quiz:** 4/4 correct
- **Exercise:** m02 PASS (16/16, no test files changed) · Wallet: hint level 3 + an extra explanation of the direction of `=`; review asked her to use `balance = balance ...` on all 3 lines, and she did · Fees: no hints (multiplied before dividing on her own); review: brackets around the return value, name `commissionFee` · Cashback: hint level 1 (compile error from a copied line using the other method's names); review: box named the same as its method
- **Mood / energy:** motivated, chose to keep going three times past 40 min; persistent on Wallet, never asked for the answer
- **Next:** quiz (incl. the new `=` direction card m02-007), then a short revisit of re-assignment; m03 lessons once the curriculum file exists
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
