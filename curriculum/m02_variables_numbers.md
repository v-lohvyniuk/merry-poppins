# Variables and numbers

**Module id:** m02_variables_numbers
**Estimated sessions:** 3
**Prerequisites:** m01_first_program

## Topic
Storing values in variables, updating them, and doing maths with whole numbers (`int`) and decimals (`double`). Plus the yes/no type `boolean`.

## Why it matters
Every automated test is full of values: an amount to transfer, an expected balance, a fee. Wrong maths in a test is worse than no test: it reports a "bug" that isn't there, or misses one that is. In payments, rounding and "which value is current" mistakes are real production bugs.

## Manual-QA bridge
- variable ≈ a field in a test-data sheet: a **name** ("balance") and a **value** (100)
- type (`int`, `double`, `boolean`) ≈ the field format: whole number, amount with decimals, yes/no checkbox
- re-assignment ≈ updating the "current balance" field after each step of a test case: the old value is gone
- integer division ≈ an ATM that only gives whole notes: 7 euros between 2 people is 3 each, and 1 stays in the machine (that 1 is `%`)
- compile error "lossy conversion" ≈ trying to type `4.50` into a field that only accepts whole numbers

## Key concepts
1. **A variable is a named box with a value**: `int balance = 100;` = type, name, `=`, starting value, `;`. `int` holds whole numbers. Use the name later instead of the number.
   - Example idea: a step counter app, `int steps = 2000;` then `System.out.println(steps);`. Ask: what prints, `steps` or `2000`?
2. **Re-assignment: a variable holds its CURRENT value**: `steps = steps + 500;` means "take what's in the box **now**, add 500, put the result back". Java works out the right side first. The old value is gone. No `int` the second time: the box already exists.
   - Example idea: a fuel gauge, `int fuel = 50; fuel = fuel - 10; fuel = fuel - 5;`. Ask: what is `fuel` now? (35, not 40 or 45.)
   - Link to her diagnostic (good to say kindly): in Task A she divided the *starting* balance, not the current one. This concept is exactly that.
3. **Maths with `int`**: `+ - * /` work as expected, except **`int / int` drops the decimals** (`7 / 2` is `3`, not `3.5`). `%` gives the leftover (`7 % 2` is `1`). Order matters: `7 / 2 * 2` is `6`, but `7 * 2 / 2` is `7`.
   - Example idea: pizza slices, `int slices = 10; int people = 3;`. Ask: what are `slices / people` and `slices % people`? (3 and 1.)
   - Link to her diagnostic: `balance / 3` printed `6`, not `6.67`. This is why.
4. **`double` for decimals, `boolean` for yes/no**: `double price = 4.50;` keeps decimals. `boolean isBlocked = false;` is only ever `true` or `false`. Pick the type that fits the data. A decimal doesn't fit into an `int` box: compile error `possible lossy conversion from double to int`. Java writes decimals with a **dot** (`4.50`), never a comma. If **either** side of a division is a `double`, the decimals stay: `10.0 / 4` is `2.5` (the trap is only `int / int`).
   - Example idea: a thermometer, `double temperature = 36.6;` and `boolean hasFever = false;`. Ask her to pick a type for: number of family members, a person's height in metres, "is the shop open?".
   - `boolean` is not used in this exercise. It's used from the first exercise of m03. Teach it here, add the card, move on.

**Given, not taught yet: "method shells".** Each exercise is a method like `static int transferFeeCents(int amountCents, int feePercent) { ... }`. Explain it as: *"The test hands in values: those are the names in brackets, and you use them like variables. `return` hands your answer back to the test. Only write the TODO lines; the rest is fixed for now."* Methods are taught properly in m06.

## Exercise
**Files she edits:**
- `exercises/src/main/java/com/merrypoppins/m02_variables_numbers/Wallet.java`
- `exercises/src/main/java/com/merrypoppins/m02_variables_numbers/Fees.java`
- `exercises/src/main/java/com/merrypoppins/m02_variables_numbers/Cashback.java`

**Tests (written by the reviewer, she does not edit):** `WalletTest.java`, `FeesTest.java`, `CashbackTest.java` in `exercises/src/test/java/com/merrypoppins/m02_variables_numbers/`
**Run:** `bash scripts/check.sh m02_variables_numbers`

**Task:**
1. **Wallet** (after concepts 1–2): `int balance = startBalance;` is given. Update `balance` for the coffee, then the groceries, then the salary (lines 21–23). The method hands back the final `balance`.
2. **Fees** (after concept 3), amounts in **cents** (`1999` = 19.99 EUR):
   - `transferFeeCents`: `feePercent` percent of `amountCents`, rounded down.
   - `splitBillShareCents`: each person's share, rounded down.
   - `splitBillLeftoverCents`: the cents left over after everyone pays their share.
3. **Cashback** (after concept 4), amounts in euros with decimals (`double`):
   - `cashback`: `ratePercent` percent of `amount`.
   - `averagePayment`: total divided by the number of payments, keeping decimals.

Before she starts: the starter already passes 1 of 16 tests (`leftoverIsZeroWhenItDividesEvenly`, because the placeholder `return 0;` happens to be right there). Expected, not a mistake.

## Done when
- [ ] `bash scripts/check.sh m02_variables_numbers` prints `Result: PASS`
- [ ] In `Wallet.java` she used **re-assignment** (`balance = balance ...`), not one long calculation in the `return` line. Tests can't check this; the tutor checks it in review. If she wrote one line, ask her to rewrite it with the three updates, because that's the concept being practised.
- [ ] She can explain in her own words why `7 / 2` is `3` in Java, and what `balance = balance - 4;` does (teach-back accepted)

## Hints for the tutor
**Wallet: wrong final balance**
- Level 1 (nudge): "After the coffee is paid, where does the new balance live? Does the next line use it?"
- Level 2 (point): "Lines 21–23: each line should start from what's in `balance` now, not from `startBalance`." (Concept 2: re-assignment.)
- Level 3 (pseudo-code):
  ```
  balance becomes (balance minus coffee)
  balance becomes (balance minus groceries)
  balance becomes (balance plus salary)
  ```

**Fees: fee is 0, or off by a few cents**
- Level 1 (nudge): "Work out 3 percent of 1999 on paper. In which order did you multiply and divide?"
- Level 2 (point): "`int / int` throws away decimals immediately. If you divide by 100 first (or calculate `feePercent / 100` first), what gets thrown away?" (Concept 3: order matters.)
- Level 3 (pseudo-code):
  ```
  fee = amount times percent, and only then divided by 100
  hand back fee
  ```

**Fees: leftover**
- Level 1 (nudge): "Remember the ATM with whole notes: what stays in the machine?"
- Level 2 (point): "There's an operator that gives exactly the leftover of a division." (Concept 3: `%`.)
- Level 3 (pseudo-code): `leftover = what remains when total is divided by people`

**Cashback: 100 times too big, or a compile error**
- Level 1 (nudge): "1.5 percent of 200 on a calculator: what do you type?"
- Level 2 (point): "Percent means 'out of 100'. Is there a `/ 100` in your line?" / for the compile error: "What type did you give your box, and does a decimal fit in it?" (Concept 4.)
- Level 3 (pseudo-code):
  ```
  cashback (a decimal box) = amount times rate, divided by 100
  hand back cashback
  ```

## Common mistakes
- **Using the start value every time** (the diagnostic mistake):
  ```
  [FAILED] WalletTest.fullDayUsesTheCurrentBalanceEachTime
      Start 100, coffee 4, groceries 30, salary 50: the balance at the end of the day should be 116
      (100 - 4 = 96, then 96 - 30 = 66, then 66 + 50 = 116) ==> expected: <116> but was: <150>
  ```
  → 150 = 100 + 50: only the last line "counted". Each line started again from `startBalance`, so the earlier payments were lost. Re-explain concept 2 with the fuel gauge.
- **Dividing too early** (`amountCents / 100 * feePercent`):
  ```
  [FAILED] FeesTest.feeIsRoundedDownToWholeCents
      A 3 percent fee on 1999 cents is 59.97, so rounded down it should be 59 cents ==> expected: <59> but was: <57>
  ```
  → `1999 / 100` is `19` (not 19.99), and `19 * 3` is `57`. The other fee tests pass, which is why careful reading of *this* message matters.
- **`feePercent / 100` first**: `2 / 100` is `0`, so every fee is `0` (`expected: <200> but was: <0>`). Same lesson: `int / int` drops decimals right away.
- **Forgot "percent" means `/ 100`**:
  ```
  [FAILED] CashbackTest.cashbackIsAPercentOfTheAmount
      1.5 percent cashback on 200.00 should be 3.00 ==> expected: <3.0> but was: <300.0>
  ```
  → "Exactly 100 times too big" is a typical clue for a missing `/ 100`.
- **Decimal in an `int` box** (`int cashback = amount * ratePercent / 100;`):
  ```
  Cashback.java line 18: incompatible types: possible lossy conversion from double to int
  ```
  → "*Lossy* = Java would have to throw away the decimals, and it refuses to do that silently. The box type must be `double`."
- **Calculation without putting it back** (`balance - coffee;`):
  ```
  Wallet.java line 21: not a statement
  ```
  → "Java calculated something but you didn't say where to keep it. Which box should get the result?"
- **`int` written again on an existing variable** (`int balance = balance - coffee;`):
  ```
  Wallet.java line 21: variable balance is already defined in method balanceAfterDay(int,int,int,int)
  ```
  → "`int balance` creates a new box. The box already exists (line 20), so just update it."
- **Capital letter in a variable name** (`Balance = balance - coffee;`):
  ```
  Wallet.java line 21: cannot find symbol
  ```
  → "`Balance` and `balance` are different names. Java doesn't know a box called `Balance`."
- **Decimal comma** (`1,5` instead of `1.5`). Likely for her, since Ukrainian uses a comma (she wrote `33,3` in the diagnostic):
  ```
  Cashback.java line 18: <identifier> expected
  ```
  → "Java decimals always use a dot. The comma confuses it into reading something else."

## Starter cards
- Q: What does `int balance = 100;` mean, part by part?
  - `int` = type: whole numbers
  - `balance` = the name of the box
  - `100` = the starting value
- Q: What does `balance = balance - 30;` do?
  - takes the CURRENT value of balance
  - subtracts 30
  - stores the result back in balance (the old value is gone)
- Q: What is `7 / 2` in Java, and why?
  - 3
  - int divided by int drops the decimals (no rounding, just cut off)
- Q: What does `%` give you? What is `10 % 3`?
  - the leftover (remainder) of a division
  - 1
- Q: When do you use `int`, `double` and `boolean`?
  - int: whole numbers (count, cents)
  - double: numbers with decimals (4.50)
  - boolean: only true or false (yes/no)
- Q: What does `possible lossy conversion from double to int` mean?
  - you're putting a decimal into a whole-number box
  - Java won't throw the decimals away silently: use double (or change the calculation)
