---
name: quiz
description: Run the spaced-repetition quiz on cards that are due today. Use at the start of every tutoring session, or when the learner asks to practise or review old topics.
allowed-tools: Read Bash(python scripts/due_cards.py *) Bash(python scripts/update_card.py *)
---

# Spaced-repetition quiz

Goal: a quick warm-up (≈5 minutes) that brings back things she is about to forget. Low pressure.

## Steps

1. Get due cards (max 7 per session, most overdue first):
   ```
   python scripts/due_cards.py --limit 7
   ```
   - `due_total` = 0 → say "No review cards today 🎉" (one line) and stop. Go back to the session flow.
   - `due_total` > 7 → only quiz the 7 returned. Mention briefly that the rest come next time.

2. Tell her how many questions (e.g. "5 quick questions from earlier, just answer in your own words").

3. For **each card, one at a time**:
   - Ask the `question` **only**. **Never show `expected_answer` before she answers.**
   - Wait for her answer.
   - **Grade on meaning**, against the key points in `expected_answer`:
     - **correct**: her answer contains the core idea of every key point (any wording, any language, small imprecision OK).
     - **wrong**: a key point is missing or wrong, or she says "don't know".
     - If it's borderline, lean **correct** when the main idea is clearly there, and add the missing detail in one sentence.
   - Feedback, at most 2–3 lines:
     - correct → confirm what was right (one line), add a nuance only if useful.
     - wrong → the right answer in simple words, with a manual-QA analogy, kindly. No lecture.
   - Record it **right away**:
     ```
     python scripts/update_card.py <id> correct
     python scripts/update_card.py <id> wrong
     ```
   - If a card has `times_wrong` ≥ 3, remember it for /wrap-up as a struggle (for the Struggles table and maybe **For reviewer**).

4. End with a one-line score ("4 of 5, nice." or "2 of 5. These need more practice, no problem."). Be honest, not gushing.

## Rules
- She can't see the JSON. Never paste it or the key points list.
- Don't turn a wrong card into a full lesson. If a topic clearly needs re-teaching, note it and suggest a short revisit after the quiz.
- If she's frustrated, it's fine to skip the remaining cards. They stay due.
