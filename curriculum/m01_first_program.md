# Your first Java program

**Module id:** m01_first_program
**Estimated sessions:** 2
**Prerequisites:** m00_sample (the toolchain works)

## Topic
What a Java program looks like, how to make it print text, and how to read the message Java gives when the code can't be built.

## Why it matters
Every automated test is a small Java program. Before writing tests she needs to run code, change what it does and see the result. Every automation engineer hits compiler errors daily: the fast ones read the message, the slow ones guess. Starting with errors she *can* solve builds the habit early.

## Manual-QA bridge
- program / `main` ≈ a test case: the steps run **top to bottom, in order**
- `System.out.println(...)` ≈ writing a line in the test report
- the exact text in quotes ≈ the "Expected result" column: capitals, spaces and punctuation all count
- compiler error ≈ the build won't even install, so testing can't start
- compiler error message ≈ a good bug report: **where** (file + line) and **what** (what Java expected)
- one broken file stops all modules ≈ one broken build blocks the whole test cycle, not just one feature

## Key concepts
1. **A program is a class with `main`**: `main` is where Java starts. It runs the lines inside its `{ }` from top to bottom.
   - Treat `public class Name { ... }` and `public static void main(String[] args) { ... }` as **fixed "magic words"** for now (given, not taught yet). `public`, `static`, `void`, `String[] args` and methods all come in later modules. The first line, `package com.merrypoppins.m01_first_program;`, is "which folder this file lives in": leave it as it is (packages are taught in m09).
   - Example idea: a "weather report" program with 2 lines in `main`. Ask her which line prints first.
2. **`System.out.println("...")` prints one line**: whatever is between the double quotes appears in the console, exactly. Each `println` = one line.
   - Example idea: printing a shopping list, one item per `println`. Ask: what changes if we swap two lines?
   - IntelliJ shortcut: type `sout` + Tab to get `System.out.println();` (T01).
3. **Statements end with `;`, comments are for humans**: every instruction ends with `;` (like a full stop). `//` and `/** ... */` are notes Java ignores. The `// TODO` lines in the exercise are comments.
   - Example idea: a 3-line snippet with one comment line. Ask her which lines Java runs.
4. **Reading a compiler error**: when the code can't be built, `check.sh` prints `COMPILE ERROR` plus the **file**, **line** and **what Java expected**. Fix the **first** error, then run again. One small mistake can produce several messages.
   - Example idea: show the real "missing `;`" output from *Common mistakes* below and ask: which file? which line? what's missing?
   - Reassure her: in the diagnostic she read a compiler error correctly. This is a skill she already has.

**Habit to start now (careful reading):** before changing code after a failing test, read the **test name** and the **message** first. They say what was expected and what actually happened.

## Exercise
**Files she edits:**
- `exercises/src/main/java/com/merrypoppins/m01_first_program/Greeting.java`
- `exercises/src/main/java/com/merrypoppins/m01_first_program/Receipt.java`

**Tests (written by the reviewer, she does not edit):** `exercises/src/test/java/com/merrypoppins/m01_first_program/GreetingTest.java`, `ReceiptTest.java` (`ConsoleCapture.java` is a test helper she can ignore)
**Run:** `bash scripts/check.sh m01_first_program`

**Task:**
1. **Greeting** (after concepts 1–2): make the program print exactly one line: `Hello, MerryBank!`
2. **Receipt** (after concept 3): line 1 is already printed. Copy its pattern to print the other three lines, in order:
   ```
   MerryBank payment receipt
   To: Coffee Corner
   Amount: 4.50 EUR
   Status: APPROVED
   ```
3. **Break it on purpose** (after concept 4, once the tests pass): delete one `;` from `Receipt.java`, run `check.sh`, read the COMPILE ERROR aloud (which file, which line, what Java expected), put the `;` back and run again until it passes.

Before she starts: the starter already passes 1 of 7 tests (the receipt title). That's expected, not a mistake.

## Done when
- [ ] `bash scripts/check.sh m01_first_program` prints `Result: PASS`
- [ ] She did the "break it on purpose" step and correctly said the file, the line and what Java expected
- [ ] She can explain in her own words what `main` is and what `println` does (teach-back accepted by the tutor)

## Hints for the tutor
**Greeting: nothing printed / "Line 1 (the greeting) is missing"**
- Level 1 (nudge): "The program runs the lines inside `main`. How many lines are there right now that Java actually runs?"
- Level 2 (point): "Look at `Greeting.java` line 15: it's a comment, so Java skips it. Look at line 19 of `Receipt.java` (the title line): that one does print."
- Level 3 (pseudo-code):
  ```
  inside main:
      print one line with the text  Hello, MerryBank!
  ```

**Receipt: a line is wrong or missing**
- Level 1 (nudge): "Read the failing test's message: which line number does it talk about, and what did it expect?"
- Level 2 (point): "Compare the `expected: <...>` and `but was: <...>` parts letter by letter: capitals, spaces after the `:`, the dot in `4.50`."
- Level 3 (pseudo-code):
  ```
  after the title line:
      print the recipient line
      print the amount line
      print the status line
  (one print per line, in this order)
  ```

**Compile error**
- Level 1 (nudge): "The build didn't install. What does the message say, and which line?"
- Level 2 (point): "Go to that exact line. Compare it with line 19 of `Receipt.java` (the title line), which works. What's different at the end, or around the quotes?"
- Level 3 (pseudo-code): "every print line = the print command, then `(`, then the text in double quotes, then `)`, then `;`"

## Common mistakes
- **Missing `;`**:
  ```
  Result: COMPILE ERROR (2 problem(s)) - the code could not be built, so no tests ran.
    src/main/java/com/merrypoppins/m01_first_program/Receipt.java line 20: ';' expected
  ```
  → "Java expected a full stop at the end of line 20." Note: `check.sh` counts each error twice (it says "2 problem(s)" but shows one). If she asks, say it's one problem and the counter is off. Not her fault.
- **Forgot the closing quote** (`"Hello, MerryBank!);`):
  ```
  Greeting.java line 15: unclosed string literal
  ```
  → "*String literal* = text in quotes. *Unclosed* = the quote was opened but never closed."
- **No quotes at all** (`System.out.println(Status: APPROVED);`), three messages for one mistake:
  ```
  Receipt.java line 22: ')' or ',' expected
  Receipt.java line 22: not a statement
  Receipt.java line 22: ';' expected
  ```
  → "Without quotes Java tries to read the text as code and gets confused, so it reports several things. All on line 22: fix the first thing and run again." Good moment for "fix the first error first".
- **Wrong capital letter in `println` or `System`**:
  ```
  Greeting.java line 15: cannot find symbol             ← Println instead of println
  Greeting.java line 15: package system does not exist   ← system instead of System
  ```
  → "Java is case-sensitive: `System` and `system` are different words to it, like two different test IDs."
- **Text differs from the expected text** (capitals, missing space, missing comma):
  ```
  [FAILED] ReceiptTest.fourthLineIsTheStatus
      Line 4 of the receipt (the status) should match exactly, including capital letters, spaces and punctuation
      ==> expected: <Status: APPROVED> but was: <Status: Approved>
  ```
  → Bug-report style: "expected `APPROVED`, actual `Approved`." Let her find the difference herself (careful-reading habit).
- **Lines in the wrong order**: the message for line 2 shows the amount text in `but was`. → "Order matters, like test steps."
- **A line is missing**: `Line 3 (the amount) is missing: the program printed only 2 line(s).`

## Starter cards
- Q: What is `main` in a Java program?
  - the place where the program starts
  - its lines run from top to bottom, in order
- Q: What does `System.out.println("...")` do?
  - prints the text between the quotes to the console
  - one `println` = one line
- Q: Why must text be inside double quotes?
  - quotes mark it as text (a String)
  - without quotes Java tries to read it as code and gives a compile error
- Q: A compile error appears. What do you look at first?
  - the file name and line number
  - the message: what Java expected (e.g. `';' expected`)
  - fix the first error first, then run again
- Q: Why does one compile error stop all modules from running?
  - the whole project is built together
  - like a build that won't install: no testing can start until it's fixed
- Q: A test fails. What do you read before changing any code?
  - the test name (what it checks)
  - the message: expected vs actual
