# Conditions: making decisions

**Module id:** m03_conditions
**Estimated sessions:** 2 (90-minute sessions)
**Prerequisites:** m02_variables_numbers

## Topic
Asking yes/no questions in code (comparisons), combining them (and / or / not), and doing different things depending on the answer (if / else, else-if chains).

## Why it matters
Business rules are conditions: "approve if there's enough money AND the card isn't blocked". Test code is full of them too. And boundaries, the "exactly the limit" cases she knows from manual testing, are where these rules break in production.

## Manual-QA bridge
- comparison (`balance >= amount`) ≈ a check in a test step that is either passed or failed: there's no "maybe"
- `&&` ≈ preconditions of a test case: ALL must be met
- `||` ≈ "any of these error messages is acceptable"
- `!` ≈ "the card is NOT blocked" in a test condition
- `if / else` ≈ a decision table with two columns
- `else if` chain ≈ a decision table read top to bottom: the first matching row wins
- `>=` vs `>` ≈ **boundary value analysis**: she already knows "exactly 100" is its own test case. Lean on this hard: it's her strength.

## Key concepts
1. **A comparison gives a boolean**: `>`, `<`, `>=`, `<=`, `==` (equal), `!=` (not equal) compare numbers and give `true` or `false`. Store it: `boolean canPay = balance >= price;` (the `boolean` from m02 finally gets used). **`=` puts a value in a box; `==` asks "are they equal?"**
   - Example idea: a parking garage, `int freeSpots = 0; boolean isFull = freeSpots == 0;`. Ask: is `isFull` true or false? What changes if `freeSpots` is 3?
   - Boundary question to ask her: "Ticket valid for 60 minutes. Is minute 60 still valid? `>` or `>=`?"
2. **Combining yes/no answers**: `&&` = AND (all must be true), `||` = OR (at least one true), `!` = NOT (flips true/false).
   - Example idea: a cinema, `boolean canEnter = hasTicket && age >= 16;`. Then `boolean getsDiscount = isStudent || isSenior;`. Ask her to fill a small truth table for one of them.
3. **`if / else`**: `if (condition) { ... } else { ... }` runs exactly one of the two blocks. The condition must be a boolean.
   - Example idea: `if (temperature > 25) { advice = "shorts"; } else { advice = "jeans"; }`. Ask: what at exactly 25?
4. **`else if` chains**: Java checks top to bottom and runs only the **first** matching branch. Two consequences: (a) for **priorities**, put the most important rule first; (b) for **ranges**, each check can assume the earlier ones were false.
   - Example idea: exam grades, `if (score >= 90) A else if (score >= 75) B else C`. Ask: what does 95 get? What if we swap the first two checks? (95 would get B, so order matters.)

**Given, not taught yet:** some methods here hand back text (`String`, taught in m04). She only writes `return "APPROVED";`: text in quotes, as in m01.

## Exercise
**Files she edits** (in `exercises/src/main/java/com/merrypoppins/m03_conditions/`):
- `TransferRules.java`
- `TransferDecision.java`
- `FeeTiers.java`

**Tests (written by the reviewer, she does not edit):** `TransferRulesTest.java`, `TransferDecisionTest.java`, `FeeTiersTest.java` in `exercises/src/test/java/com/merrypoppins/m03_conditions/`
**Run:** `bash scripts/check.sh m03_conditions`

**Task** (amounts are in cents, as in m02):
1. **TransferRules** (after concepts 1–2):
   - `hasEnoughMoney`: the balance is at least the amount.
   - `isWithinDailyLimit`: the amount is not more than the limit.
   - `canTransfer`: enough money AND within the limit AND the card NOT blocked.
2. **TransferDecision.decide** (after concept 3): `"APPROVED"` or `"DECLINED"` with if / else.
3. **FeeTiers.feePercent** (after concept 4): 2 / 1 / 0 percent by amount range.
4. **TransferDecision.declineReason** (after concept 4): the first rule that fails, in a given priority order, or `"NONE"`.

Before she starts: the starter already passes 7 of 26 tests, because the placeholder `return false;` / `return 0;` happens to be right for some cases. Expected, not a mistake. Tip for her: run the tests after each method, not only at the end.

## Done when
- [ ] `bash scripts/check.sh m03_conditions` prints `Result: PASS`
- [ ] She can explain the difference between `=` and `==`, and between `&&` and `||` (teach-back accepted)
- [ ] She can say why `declineReason` checks the blocked card **first** (priority = order of the else-if chain)

## Hints for the tutor
**Boundary tests fail ("exactly enough is enough", "exactly the limit is OK")**
- Level 1 (nudge): "This is boundary value analysis, like in your manual testing. What should happen at *exactly* the limit?"
- Level 2 (point): "Look at the comparison sign. Does `>` include the equal case?" (Concept 1.)
- Level 3 (pseudo-code): `enough = balance is greater than OR EQUAL to amount`

**canTransfer: too many transfers allowed**
- Level 1 (nudge): "Read the failing test name. Which rule should have stopped the transfer?"
- Level 2 (point): "You need ALL rules to be true. Which operator means 'all'?" / "How do you say 'NOT blocked'?" (Concept 2.)
- Level 3 (pseudo-code): `allowed = enough money AND within limit AND NOT blocked`

**decide / compile errors around if**
- Level 1 (nudge): "Read the compile message and the line. What did Java expect?"
- Level 2 (point): "Compare your line with the shape `if (condition) { ... } else { ... }`: anything extra between `)` and `{`?" (Concept 3.)
- Level 3 (pseudo-code):
  ```
  if balance is at least amount
      hand back APPROVED
  otherwise
      hand back DECLINED
  ```

**feePercent: wrong at exactly 10000 or 100000**
- Level 1 (nudge): "Which group does exactly 100 EUR belong to, according to the comment?"
- Level 2 (point): "Check the sign in each `if`: `<` or `<=`? And does the order of your checks matter for ranges?" (Concept 4.)
- Level 3 (pseudo-code):
  ```
  if amount is less than 10000        -> 2
  else if amount is less than 100000  -> 1
  else                                -> 0
  ```

**declineReason: the right reasons, but the wrong one wins**
- Level 1 (nudge): "Read the test name: which reason must win when several rules fail?"
- Level 2 (point): "Java runs only the FIRST matching branch. Is your first check the most important rule?" (Concept 4: priority.)
- Level 3 (pseudo-code):
  ```
  if card blocked                  -> CARD_BLOCKED
  else if amount over the limit    -> OVER_DAILY_LIMIT
  else if balance less than amount -> INSUFFICIENT_FUNDS
  else                             -> NONE
  ```

## Common mistakes
(Line numbers depend on how she writes her code, so they're not given here; `check.sh` shows her actual line.)
- **`>` instead of `>=`** (boundary):
  ```
  [FAILED] TransferRulesTest.enoughMoneyWhenBalanceIsExactlyTheAmount
      hasEnoughMoney(balance 1000, amount 1000) should be true: exactly enough is enough ==> expected: <true> but was: <false>
  ```
  → Only the "exactly" test fails, so all the other tests passing is misleading. Classic boundary bug: ask her what she'd write in a bug report.
- **`||` instead of `&&`**: three `cannotTransfer...` tests fail with `expected: <false> but was: <true>`. → "Your rule lets the transfer through if ANY rule is OK. The bank wants ALL rules OK."
- **Wrong order in declineReason**:
  ```
  [FAILED] TransferDecisionTest.cardBlockedIsReportedFirstEvenIfEverythingElseFails
      ... so it should be CARD_BLOCKED ==> expected: <CARD_BLOCKED> but was: <INSUFFICIENT_FUNDS>
  ```
  → The single-rule tests pass. Only the "several rules fail" tests catch the order. That's why those test cases exist.
- **`<=` instead of `<` in feePercent**: `feePercent(10000 cents) should be 1 ... expected: <1> but was: <2>`. → Boundary again: which group is exactly 100 EUR in?
- **`=` instead of `==` (or `<`) inside if**:
  ```
  FeeTiers.java line …: incompatible types: int cannot be converted to boolean
  ```
  → "One `=` puts 10000 into the box. `if` needs a yes/no question, and a number isn't a yes/no answer."
- **`=<` instead of `<=`**, with a confusing message:
  ```
  TransferRules.java line …: > or ',' expected
  ```
  → "Java doesn't know `=<`. The sign is always `<=` (the same order as you say it: 'less than or equal')." A good example that the message can be weird while the *line* is still exactly right.
- **`;` right after `if (...)`**:
  ```
  TransferDecision.java line …: 'else' without 'if'
  ```
  → "The `;` ended the if right there, so the `else` has no if to belong to."
- **No final `else`** (e.g. `else if (amount >= 100000)` at the end):
  ```
  FeeTiers.java line …: missing return statement
  ```
  → "Java isn't sure one of your branches will always run, so it's possible nothing gets handed back. A plain `else` at the end fixes that."
- **Text without quotes** (`return APPROVED;`):
  ```
  TransferDecision.java line …: cannot find symbol
  ```
  → "Without quotes, Java looks for a box called APPROVED. Text needs double quotes (m01)."

## Starter cards
- Q: What is the difference between `=` and `==`?
  - `=` puts a value into a variable
  - `==` asks whether two values are equal (gives true or false)
- Q: What do `&&`, `||` and `!` mean?
  - `&&` = AND: all must be true
  - `||` = OR: at least one must be true
  - `!` = NOT: flips true and false
- Q: The rule is "at least 100". Which sign do you use, and which values would you test?
  - `>=` (100 itself is allowed)
  - test 99, 100 and 101 (boundary value analysis)
- Q: In an `if / else if / else` chain, how many branches run?
  - only one: the first whose condition is true
  - so the order of the checks matters (priority, ranges)
- Q: What does `missing return statement` mean after an else-if chain?
  - Java can't be sure some branch always hands back a value
  - add a final plain `else`
