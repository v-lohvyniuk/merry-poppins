<!--
HOW TO USE THIS TEMPLATE (for the curriculum author)
- Copy to curriculum/<module-id>.md, e.g. curriculum/m01_variables.md.
- <module-id> must match the Java package: exercises/src/{main,test}/java/com/merrypoppins/<module-id>/
- Modules are taught in file-name order (m01, m02, ...). Files starting with "_" are ignored.
- The tutor reads this file in /lesson and /review. Write for the tutor: it turns these notes into
  small ELI5 chunks itself, so bullet points are enough. Keep "Done when" objective.
- Delete these comments if you like; the tutor ignores HTML comments.
-->

# <Module title>

**Module id:** m00_example
**Estimated sessions:** 2
**Prerequisites:** <module ids or "none">

## Topic
<One or two sentences: what this module is about.>

## Why it matters
<Why an automation QA needs this. Tie it to real work: a test suite, a flaky test, a bug report.>

## Manual-QA bridge
<Analogies the tutor should prefer. Optional but very useful.>
- <concept> ≈ <manual-QA thing>   e.g. "method ≈ a reusable test step"

## Key concepts
<!-- Teach in this order, one concept per chunk. Each concept should take ≤ 5 minutes. -->
1. **<Concept>**: <the one thing she must understand>
   - Example idea: <a tiny example the tutor can show, NOT the exercise solution>
2. **<Concept>**: ...
3. **<Concept>**: ...

## Exercise
**Files she edits:** `exercises/src/main/java/com/merrypoppins/m00_example/<File>.java`
**Tests (written by the reviewer, she does not edit):** `exercises/src/test/java/com/merrypoppins/m00_example/<File>Test.java`
**Run:** `bash scripts/check.sh m00_example`

**Task:**
<What she has to build, in plain words. Steps if needed.>

## Done when
- [ ] `bash scripts/check.sh m00_example` prints `Result: PASS`
- [ ] She can explain <concept X> in her own words (teach-back accepted by the tutor)
- [ ] <optional extra objective check>

## Hints for the tutor
<!-- Optional. Pre-written rungs of the hint ladder for the tricky parts. Never beyond pseudo-code. -->
- Level 1 (nudge): ...
- Level 2 (point to line/concept): ...
- Level 3 (pseudo-code): ...

## Common mistakes
- <mistake>: <what it looks like in the compiler or test output> → <how to explain it>
- ...

## Starter cards
<!-- The tutor adds these with scripts/add_card.py when the concept is first taught.
     Key points = what a correct answer must contain (meaning, not exact wording). -->
- Q: <question>
  - <key point>
  - <key point>
