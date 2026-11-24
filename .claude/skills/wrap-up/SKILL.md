---
name: wrap-up
description: End the tutoring session. Save progress to state/progress.md, add spaced-repetition cards for what she struggled with, flag items for the reviewer, and remind her to commit. Use at the end of every session, or when she says she's done or wants to stop.
allowed-tools: Read Bash(python scripts/rewards.py *) Bash(python scripts/add_card.py *) Bash(python scripts/due_cards.py *) Bash(date *) Bash(git status *)
---

# Wrap-up: save state, then let her go

Do the saving quietly and quickly. She is probably tired. Her part is at the end and should be short.

## 1. Session facts
- Read `state/.session.json` for `started_at`. Duration = now − started_at, in minutes. If the file is missing, write "unknown".
- Read `state/progress.md`.

## 2. Update `state/progress.md` (Edit tool; keep the structure)

**a. Header block at the top:**
- `Current module`: move to the next module (next `curriculum/mNN_*.md` in file-name order) **only if** the module's exercise passed `check.sh` this session and the other **Done when** items are met.
- `Current step`: where she stopped (e.g. "concept 3 of 5 taught", "exercise started, failing test X").
- `Next session starts with`: one concrete line (e.g. "revisit `==` vs `.equals()`, then concept 4").
- `Sessions so far`: +1.

**b. New session entry**, inserted **directly below** the `<!-- /wrap-up inserts ... -->` line (newest first):

```markdown
### Session N · YYYY-MM-DD · NN min · <module>
- **Covered:** <concepts / exercise work, short>
- **Teach-back:** <concept: first try ✅ / after re-explaining 🔁 / not yet ❌>
- **Quiz:** <x/y correct> (or "no cards due")
- **Exercise:** <not started | in progress: what's failing | PASS> · highest hint level: <0–3>
- **Mood / energy:** <one honest phrase, e.g. "focused", "tired after 30 min", "frustrated at loops">
- **Next:** <same as "Next session starts with">
```

**c. Struggles / revisit table:** add or update one row per topic she struggled with (teach-back failed twice or more, quiz card wrong, hint level 3 needed). Update existing rows; don't duplicate. Move status `open → improving → solid` based on evidence from today.

**d. For reviewer:** add `- [ ] YYYY-MM-DD · <module> · <what> · <why>` (newest first) for:
- she asked for the answer directly 2+ times, or needed hint level 3
- a concept failed teach-back 3 times (curriculum may need a different explanation)
- test files under `exercises/src/test` were modified
- code that looks copied from elsewhere, or a pattern the lead should correct personally
- a missing curriculum file, a broken test, a setup problem, or a guardrail that blocked something you think it shouldn't
- anything else a human mentor should know (motivation dips, a recurring confusion)

If nothing qualifies, add nothing. Don't pad.

## 3. Cards for struggles
For each real struggle today (max 3), add one card focused on the exact confusion:
```
python scripts/add_card.py --module <module> --question '<question>' --point '<key point>' --point '<key point>'
```
One line, single quotes, no `$` or backticks. Skip if a card for it already exists (the script refuses exact duplicates).

## 4. Close the session clock
Overwrite `state/.session.json` with the same `started_at` plus `"ended_at": "<now ISO time>"`.

## 4b. Rewards (streaks and badges)
- Run `python scripts/rewards.py wrapup`. If it prints `REWARDS DISABLED`, skip the rest of this step and say nothing about rewards.
- Before that: if in this session she clearly met a tutor badge's `when` rule (see `gamification.json`), award it with `python scripts/rewards.py award <id>`.
- Keep the printed lines (🔥 streak, 🏅 new badges) for step 5.

## 5. Talk to her (short!)
- **2–3 lines:** what she did today (specific, honest), and one thing to look forward to next time.
- Remind her to commit. She runs this herself in a terminal in the project folder:
  ```
  git add -A
  git commit -m "Session N: <short summary>"
  ```
- If step 4b printed streak/badge lines, add them as they are, in one short extra line. No fuss.
- Then stop. No new teaching.
