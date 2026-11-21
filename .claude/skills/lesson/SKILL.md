---
name: lesson
description: Teach the next concept from the learner's current curriculum module in small ELI5 chunks with manual-QA analogies and teach-back. Use after the quiz when there is new material, or when she asks to learn the next thing.
argument-hint: "[module-id or concept]"
allowed-tools: Read Glob Grep Bash(python scripts/add_card.py *)
---

# Lesson: teach the next concept

## 1. Find where she is
- Read `state/progress.md`: **Current module**, **Current step**, **Next session starts with**, open rows in **Struggles / revisit**.
- Read `curriculum/<current module>.md`. If `$ARGUMENTS` names a module or concept, use that instead.
- **No curriculum file** for the module → tell her this module isn't ready yet. Add `- [ ] <date> · <module> · curriculum file missing` under **For reviewer** in `state/progress.md`. Offer to revisit an open struggle instead.
- Pick the **next untaught key concept**, in the order listed. If an open struggle belongs to this module, consider a 2-minute revisit first.

## 2. Teach ONE concept
Loop for one concept only:

1. **Hook**: one line connecting it to manual testing (use the module's *Manual-QA bridge* or CLAUDE.md's table).
2. **Explain**: ≤ ~6 short lines, ELI5, no jargon without explanation.
3. **Example**: at most one tiny snippet (≤ 8 lines) in a **different domain than the exercise**. Nothing copyable into her exercise.
4. **Check**: one small question. A prediction ("what will this print?") or "spot the bug" works best.
5. **Teach-back**: "Can you explain <concept> in your own words, like you'd explain it to a junior manual tester?"
   - right → one line on what was right, then go on.
   - partly → name the right part, fix the missing part in ≤ 2 sentences, ask again briefly.
   - wrong → **re-explain differently**: new analogy, smaller example, simpler words. Not longer.
   - 3 misses → stop. Short recap, note it as a struggle, move on or end the topic for today.

6. **Card**: when the concept is accepted, add 1 card. Use the curriculum's *Starter cards* if present:
   ```
   python scripts/add_card.py --module <module> --question '<question>' --point '<key point>' --point '<key point>'
   ```
   One line, single quotes, no `$` or backticks in the text.

Then pause: "Ready for the next one, or want to try the exercise?" Never chain two concepts in one message.

## 3. When the module's concepts are covered
- Introduce the **Exercise**: which file to open, what to build (in plain words), how to run it (`bash scripts/check.sh <module>`), and what "done" means (from **Done when**).
- She writes the code in her editor. You do not.
- Say she can ask for a hint any time, and that `/review` checks her work.

## Remember for /wrap-up
Which concepts were taught, the teach-back results (first try / after re-explaining / not yet), and which analogy worked.
