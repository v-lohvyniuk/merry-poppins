# Strings: working with text

**Module id:** m04_strings
**Estimated sessions:** 2 (90-minute sessions)
**Prerequisites:** m03_conditions

## Topic
The `String` type for text: gluing text together, using String methods (length, capitals, trimming, searching), and comparing text the right way with `equals`.

## Why it matters
Most of what automated tests check is text: a status in an API response, a label on a button, an error message, a currency code. Comparing text with `==` is the classic junior bug in test code: the test passes on your machine and fails against the real API. Interviewers ask about it too.

## Manual-QA bridge
- String ≈ any text field in the app: name, IBAN, status
- `+` (concatenation) ≈ building an expected message from test data: "Sent " + amount + " EUR"
- `trim()` / `toUpperCase()` ≈ "the field should accept ' eur ' and save it as 'EUR'": input normalisation she has tested many times
- `contains` ≈ "the error message should contain 'insufficient'"
- `equals` vs `==` ≈ "same text" vs "the very same piece of paper". Two printed copies of the same receipt have the same text, but they're different sheets.
- text from an API ≈ what she saw in Charles/Proxyman: it comes from outside, not from your own code

## Key concepts
1. **`String` is the text type, `+` glues text**: `String city = "Lviv";`, `"Welcome to " + city` → `"Welcome to Lviv"`. Numbers glued into text become text. Java works **left to right**: `"Total: " + 2 + 3` is `"Total: 23"` (text + 2 = text, then + 3). Parentheses make the maths happen first: `"Total: " + (2 + 3)` → `"Total: 5"`. Spaces only appear where you put them.
   - Example idea: a weather app, `"Today in " + city + ": " + degrees + " C"`. Ask: what's the result for Lviv and 7? Then ask about `"Sum: " + 1 + 1` (the trap).
2. **String methods with a dot**: `text.length()` (number of characters), `toUpperCase()`, `toLowerCase()`, `trim()` (removes spaces at the start and end), `startsWith("...")`, `contains("...")`. They can be chained: `name.trim().toUpperCase()`. **Important: they hand back a NEW String; the original doesn't change.** `name.trim();` on its own throws the result away.
   - Example idea: a username field, `String raw = "  Olena ";`. Ask: what is `raw.trim()`? What is `raw.length()` (8) vs `raw.trim().length()` (5)? What is `raw` after `raw.trim();` alone? (Still `"  Olena "`.)
   - The `()` matter: `length()` is an action you ask the text to do.
3. **Comparing text: `equals`, not `==`**: `status.equals("APPROVED")` asks "same letters?". `==` asks "the very same object?". Text written in your own code can *seem* to work with `==`; text from outside (API, file, user input) usually doesn't. `equalsIgnoreCase` ignores capitals. Contrast with m03: `==` is correct for **numbers**, wrong for **text**.
   - Example idea: two library cards with the same name printed on them. Ask: same name? (`equals`: yes.) Same card? (`==`: no.)
   - Link to her diagnostic (kindly): Q7 asked exactly this, and now she'll know.

## Exercise
**Files she edits** (in `exercises/src/main/java/com/merrypoppins/m04_strings/`):
- `Messages.java`
- `TextCleanup.java`
- `StatusChecks.java`

**Tests (written by the reviewer, she does not edit):** `MessagesTest.java`, `TextCleanupTest.java`, `StatusChecksTest.java` in `exercises/src/test/java/com/merrypoppins/m04_strings/`
**Run:** `bash scripts/check.sh m04_strings`

**Task:**
1. **Messages** (after concept 1):
   - `greeting`: `Hello, Anna!`
   - `transferLine`: `Sent 25 EUR to Anna`
   - `totalLine`: `Total: 5 EUR`, where the two numbers must be **added**.
2. **TextCleanup** (after concept 2):
   - `normalizeCurrency`: `"  eur "` → `"EUR"`.
   - `isUkrainianIban`: starts with `UA` AND is 29 characters long.
   - `mentionsRefund`: the word "refund" in any capitals, anywhere in the text.
3. **StatusChecks** (after concept 3):
   - `isApproved`: is the status `APPROVED`?
   - `isFinal`: `APPROVED` or `DECLINED`.
   - `isSameCurrency`: the same code, ignoring capitals.

The StatusChecks tests pass text "as if it came from an API" (a fresh String). That's what makes `==` fail, just like in real test code.

Before she starts: the starter already passes 6 of 21 tests (the "should be false" cases). Expected, not a mistake.

## Done when
- [ ] `bash scripts/check.sh m04_strings` prints `Result: PASS`
- [ ] She can explain why `==` is wrong for text and `equals` is right, with her own analogy (teach-back accepted)
- [ ] She can say what `"Total: " + 2 + 3` gives and why

## Hints for the tutor
**Messages: spaces or punctuation wrong**
- Level 1 (nudge): "Compare expected and actual letter by letter. Where are the spaces?"
- Level 2 (point): "Java only puts spaces where you put them inside the quotes." (Concept 1.)
- Level 3 (pseudo-code):
  ```
  glue together, in this order:
      the word Sent and a space
      the amount
      a space
      the currency
      the word to, with a space on both sides
      the recipient
  ```

**totalLine: `Total: 23 EUR`**
- Level 1 (nudge): "The test expected 5 but got 23. Where did 23 come from?"
- Level 2 (point): "Java reads `+` left to right. Once there's text on the left, what does `+ 2` do?" (Concept 1: the trap.)
- Level 3 (pseudo-code): `text "Total: " + (add the two payments FIRST) + text " EUR"`

**normalizeCurrency: spaces still there (`< EUR >`)**
- Level 1 (nudge): "You called trim. Where did its result go?"
- Level 2 (point): "String methods hand back a NEW text; the original doesn't change. Did you keep the new one?" (Concept 2.)
- Level 3 (pseudo-code): `clean = input, trimmed, then upper-cased (in one chain)`

**isUkrainianIban / mentionsRefund**
- Level 1 (nudge): "Which two rules must both be true?" / "What if the word is in capitals?"
- Level 2 (point): "You'll need `&&` from m03 and `length()`." / "Make the description all lower case first, then search." (Concept 2.)
- Level 3 (pseudo-code): `starts with UA AND length is 29` / `description in lower case contains "refund"`

**StatusChecks: `expected: <true> but was: <false>` for APPROVED**
- Level 1 (nudge): "The status IS approved, but your method says no. How are you comparing the text?"
- Level 2 (point): "`==` asks 'the same object?', not 'the same letters?'. The text came from the API." (Concept 3.)
- Level 3 (pseudo-code): `approved = status has the same letters as "APPROVED"`

## Common mistakes
(Line numbers depend on her code, so they're shown as `…`.)
- **`==` on Strings**:
  ```
  [FAILED] StatusChecksTest.approvedStatusFromAnApiIsApproved
      isApproved should be true for the status APPROVED that came back from the API ==> expected: <true> but was: <false>
  ```
  → The `PENDING` test still passes, so it looks "almost right". Concept 3, with the library-card analogy. This is the module's main point: let her discover it, don't rush.
- **Missing parentheses in the total**:
  ```
  [FAILED] MessagesTest.totalLineAddsTheTwoAmounts
      totalLine(2, 3) should show the SUM, 2 + 3 = 5 ==> expected: <Total: 5 EUR> but was: <Total: 23 EUR>
  ```
  → "23 is 2 and 3 glued together as text."
- **trim result thrown away** (`input.trim();` on its own line):
  ```
  [FAILED] TextCleanupTest.currencyLosesSpacesAroundIt
      normalizeCurrency(" eur ") should have no spaces around it and be all capitals ==> expected: <EUR> but was: < EUR >
  ```
  → Note: `check.sh` squeezes several spaces into one in messages, so `"  eur "` shows as `" eur "`. The spaces in `< EUR >` are the clue.
- **Case-sensitive search or comparison**: `mentionsRefund("REFUND for order 1042") should be true: capitals don't matter ==> expected: <true> but was: <false>`, or the same for `isSameCurrency("eur", "EUR")`.
- **`&&` instead of `||` in isFinal**: `isFinal should be true for the status APPROVED ... expected: <true> but was: <false>`. → "Can a status be APPROVED and DECLINED at the same time?"
- **`"APPROVED" || "DECLINED"` inside one equals**:
  ```
  StatusChecks.java line …: bad operand types for binary operator '||'
  ```
  → "`||` joins two yes/no answers, not two texts. Ask two separate questions and join the answers."
- **`length` without `()`**, **`String` with a small s**, **text without quotes**: all give
  ```
  … line …: cannot find symbol
  ```
  → "Java doesn't recognise a name on this line. Check spelling, capitals and `()`."
- **Single quotes** (`'UA'`):
  ```
  TextCleanup.java line …: unclosed character literal
  TextCleanup.java line …: not a statement
  ```
  → "In Java, text is always in double quotes. Single quotes are for one single character." (Several messages for one mistake: fix the first one.)

## Starter cards
- Q: What is `"Total: " + 2 + 3` in Java, and how do you get `Total: 5`?
  - `Total: 23` (left to right: text + 2 is text, then + 3)
  - put the maths in parentheses: `"Total: " + (2 + 3)`
- Q: What does `name.trim();` on its own line do to `name`?
  - nothing: String methods hand back a NEW String
  - you must use or store the result: `name = name.trim();`
- Q: Why use `equals` instead of `==` for Strings?
  - `==` checks if it's the very same object
  - `equals` checks if the letters are the same
  - text from an API/file is a different object, so `==` fails
- Q: When is `==` correct?
  - for numbers (int, double) and booleans
  - not for text
- Q: How do you compare two currency codes ignoring capitals?
  - `first.equalsIgnoreCase(second)`
- Q: What do `length()`, `trim()` and `contains("x")` do?
  - number of characters
  - removes spaces at the start and end
  - true if the text has "x" somewhere in it
