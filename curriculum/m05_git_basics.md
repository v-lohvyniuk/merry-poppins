# Git basics: saving and sharing your work

**Module id:** m05_git_basics
**Estimated sessions:** 2 (90-minute sessions)
**Prerequisites:** m04_strings

## Topic
The terminal on her Mac, and what Git really does when she runs `git add`, `commit`, `push` and `pull`: the three places a change can be, reading `git status`, `.gitignore`, and being ahead of or behind GitHub.

## Why it matters
Every automation team keeps its tests in Git. On day one at work she'll clone a repo, make a branch, commit and push. A junior who reads `git status` calmly, knows why `target/` is never committed and pulls before pushing doesn't break the team's build. She already runs these commands at the end of every session. This module turns "copy the commands from the tutor" into "I know what each one does".

**What she has already lived through (use it, kindly):** a real merge with GitHub where an editor opened (the merge message kept Git's `#` lines, so the editor caught her by surprise), and a push that failed with 403 until she got access. Both make perfect examples.

## Manual-QA bridge
- terminal ≈ Finder, but you type instead of click
- `git status` ≈ the status column in Jira: where is each change right now?
- working folder → staging → commit ≈ test case: **draft → ready for review → approved and saved**
- `git add` ≈ "move this test case to *ready for review*". You choose which ones.
- commit ≈ a saved test run in TestRail: a snapshot with a date and a description you can always go back to
- commit message ≈ a bug title: short, says what and why
- `.gitignore` ≈ "don't attach these to the bug report": build output, IDE files, logs, passwords
- push / pull ≈ syncing your local test plan with the shared one on the server
- ahead / behind ≈ "my copy has changes the server doesn't" / "the server has changes I don't"
- 403 ≈ a test account without the right role: the request is fine, the permission isn't

## Key concepts
1. **Terminal basics (Mac)**: Terminal app (or IntelliJ's Terminal tab). The prompt ends with `%`. `pwd` = where am I, `ls` = what's here, `ls -a` = also hidden files (names starting with `.`, like `.gitignore`), `cd exercises` = go into a folder, `cd ..` = one folder up. **Tab** completes names, **↑** brings back the last command.
   - Example idea: open Terminal, `pwd`, then `ls`. Ask: "what do you see, and in which folder are you?" Then `ls -a`: "what new names appeared, and why were they hidden?"
2. **Git's three places**: the **working folder** (your files), the **staging area** (`git add` puts a change here), and the **history** (`git commit` saves everything staged as one snapshot). `git status --short` shows where each change is, with a 2-letter code: `??` untracked (never saved), ` M` changed but not staged, `M ` staged, `A ` new file staged, `MM` staged and then changed again. **First letter = staging, second letter = working folder.**
   - Example idea (non-code): a recipe book. Edit a recipe card on the table (working folder), put it in the "to file" tray (staging), then file the whole tray as one dated bundle (commit). Ask: "if I edit a card that's already in the tray, where is that new edit?" (On the table, not in the tray: that's `MM`.)
3. **A commit is a saved snapshot with a message**: `git log --oneline` lists them, newest first. `git diff` shows what changed but isn't staged yet. A good message says **what and why** in one short line ("Add fee tiers for transfers", not "update" or "asdf").
   - Example idea: show `git log --oneline` of her own repo (the tutor may run it). Ask: "which of these messages would help you find a change in 3 months?"
4. **`.gitignore`**: a list of files Git should never save: build output (`target/`), IntelliJ files (`.idea/`), logs (`*.log`), compiled files (`*.class`), secrets (`.env`). Rules match the **start** of a path (a folder), the **end** (a file type) or an **exact name**. New String method: `endsWith(".log")`, the same idea as `startsWith`, but at the end.
   - Example idea: open her repo's `.gitignore` with her (it has `target/`). Ask: "after you run the tests, does `git status` show `exercises/target/`? Why not?"
5. **Remote: push and pull**: GitHub keeps its own copy. **Ahead** = your commits GitHub doesn't have yet (push them). **Behind** = commits on GitHub you don't have yet (pull them first). Rule: **commit, then pull, then push**. If `git pull` opens an editor for a merge message: in vim type `:wq` and Enter. To avoid the editor, use `git pull --no-edit`. **403** = GitHub says "you're not allowed": a permission problem, not her code.
   - Example idea: show a made-up `git status` line "Your branch is behind 'origin/main' by 2 commits". Ask: "what do you do before pushing?"

**Re-used on purpose (she struggles with these, see Struggles):**
- **The direction of `=`** in `GitCounter`: `notPushed = notPushed + newCommits;` changes the box on the LEFT. Before the exercise, ask her: "which box changes, and which one is only read?"
- **`!`** in `canPushNow` ("NO uncommitted changes").

## Exercise
**Files she edits** (in `exercises/src/main/java/com/merrypoppins/m05_git_basics/`):
- `StatusLines.java`
- `GitCounter.java`
- `IgnoreRules.java`
- `PushCheck.java`

**Tests (written by the reviewer, she does not edit):** `StatusLinesTest.java`, `GitCounterTest.java`, `IgnoreRulesTest.java`, `PushCheckTest.java` in `exercises/src/test/java/com/merrypoppins/m05_git_basics/`
**Run:** `bash scripts/check.sh m05_git_basics`

**Task:**
1. **StatusLines** (after concept 2): classify one line of `git status --short`. Is it untracked? Staged? Does it have unstaged changes? (`startsWith`, `||`.)
2. **GitCounter.notPushedYet** (after concepts 3 and 5): count the commits that aren't on GitHub at the end of a day, updating ONE box step by step. The push resets it.
3. **IgnoreRules.shouldIgnore** (after concept 4): five `.gitignore`-style rules joined with `||`.
4. **PushCheck** (after concept 5):
   - `pushAdvice`: what to do next, in priority order (COMMIT_FIRST → PULL_FIRST → NOTHING_TO_PUSH → PUSH).
   - `canPushNow`: all three conditions together, with `!`.
5. **Practical, in her own Terminal** (any time after concept 5):
   1. `pwd` and `ls -a` in the project folder: find `.gitignore`. Then `cd exercises`, `ls`, `cd ..`.
   2. Change one of her m05 files, run `git status --short`, and say what ` M` means. Then `git add` that file, run `git status --short` again, and say what changed (now `M `).
   3. Commit with a message that says what and why.
   4. Read the "ahead of 'origin/main' by N commits" line in `git status`. Then `git pull --no-edit`, then `git push`.

Before she starts: the starter already passes 10 of 32 tests (the "should be false" cases). Expected, not a mistake.

## Done when
- [ ] `bash scripts/check.sh m05_git_basics` prints `Result: PASS`
- [ ] Practical steps 1–4 done. The tutor checks with `git log -1 --stat` (a new commit with a what-and-why message touching m05 files) and `git status` ("up to date with 'origin/main'" after the push).
- [ ] She can explain the three places (working folder, staging, history) and what "behind" means, in her own words (teach-back accepted)

## Hints for the tutor
**StatusLines: `?? Main.java` counted as staged**
- Level 1 (nudge): "Where exactly is the status code in the line, and where is the file name?"
- Level 2 (point): "`contains("M")` finds an M anywhere, even in `Main.java`. Which String method only looks at the very beginning?" (m04 concept 2.)
- Level 3 (pseudo-code): `staged = the line STARTS with M, or the line STARTS with A`

**StatusLines: `MM` / `AM` not counted as unstaged**
- Level 1 (nudge): "Which letter is about the working folder, the first or the second?"
- Level 2 (point): "The Javadoc lists three starts that have M as the second letter. How many does your check cover?"
- Level 3 (pseudo-code): `unstaged = starts with " M" OR starts with "MM" OR starts with "AM"`

**GitCounter: wrong count**
- Level 1 (nudge): "Tell me the story with the numbers from the failing test. How many commits are waiting after each step?"
- Level 2 (point): "Look at your update lines. Which box is on the LEFT of `=`? That's the one that changes. Is it `notPushed` every time?" (m02 concept 2; her open struggle.) / For the reset: "What does 'nothing is waiting' mean as a number?"
- Level 3 (pseudo-code):
  ```
  notPushed becomes notPushed plus newCommits
  if she pushed: notPushed becomes 0
  notPushed becomes notPushed plus laterCommits
  ```

**IgnoreRules: `.env.example` or `target-audience.md` ignored**
- Level 1 (nudge): "Read the reason in the test message. Is that file really a secret / really in the build folder?"
- Level 2 (point): "`contains` matches anywhere. Which rule needs 'starts with', and which needs 'exactly'?" (Concept 4.)
- Level 3 (pseudo-code): `starts with "target/" OR starts with ".idea/" OR ends with ".log" OR ends with ".class" OR is exactly ".env"`

**PushCheck**
- Level 1 (nudge): "Read the test name: which advice must win when several things are true?" / "Which of the three conditions does the failing case break?"
- Level 2 (point): "Java runs only the first matching branch (m03 concept 4)." / "Are you comparing `ahead` with `behind`? The rule compares each one with a number." / "How do you say NO uncommitted changes? (m03: `!`)"
- Level 3 (pseudo-code):
  ```
  if uncommitted changes     -> COMMIT_FIRST
  else if behind more than 0 -> PULL_FIRST
  else if ahead is 0         -> NOTHING_TO_PUSH
  else                       -> PUSH

  can push = ahead more than 0 AND behind is 0 AND NOT uncommitted
  ```

## Common mistakes
(Line numbers depend on her code, so they're shown as `…`. Note: `check.sh` squeezes double spaces in messages, so `"M  Wallet.java"` appears as `"M Wallet.java"`. Each message names the letters in words for that reason.)
- **`=` in the wrong direction** (`newCommits = notPushed + newCommits;`). This is her open struggle:
  ```
  [FAILED] GitCounterTest.laterCommitsWithoutPushAddUp
      notPushedYet(ahead at start 0, new commits 3, pushed false, later commits 1) should be 4: 0 + 3 + 1, no push ==> expected: <4> but was: <1>
  ```
  → The new commits went into the wrong box, so `notPushed` never got them. Ask "which box is on the left?" before explaining.
- **`if (pushed = true)`**: it **compiles**, but `=` *puts* `true` into `pushed`, so the push branch always runs. The two "no push" tests fail, e.g. `notPushedYet(ahead at start 0, new commits 3, pushed false, later commits 1) should be 4 ... ==> expected: <4> but was: <1>` and `newCommitsAreAdded ... expected: <3> but was: <0>`. → `=` vs `==` again (m03). Simplest fix in words: `if (pushed)` already asks the question.
- **`contains` instead of `startsWith` / `equals`**:
  ```
  isStaged(" M Wallet.java") should be false: the first letter is a space, so nothing is staged ==> expected: <false> but was: <true>
  shouldIgnore("docs/target-audience.md") should be false: it is not inside the target/ folder ... ==> expected: <false> but was: <true>
  shouldIgnore(".env.example") should be false: only the exact name .env is secret ... ==> expected: <false> but was: <true>
  ```
  → In testing terms: a check that's too loose gives false positives.
- **Comparing the wrong pair** (`ahead > behind` in canPushNow):
  ```
  canPushNow(ahead 2, behind 1, uncommitted false) should be false: behind by 1, so pull first ==> expected: <false> but was: <true>
  ```
- **Forgot `!`**: `canPushNow(ahead 2, behind 0, uncommitted true) should be false: commit first ==> expected: <false> but was: <true>`.
- **Wrong priority in pushAdvice**: `... should be COMMIT_FIRST: uncommitted changes are checked first ==> expected: <COMMIT_FIRST> but was: <PULL_FIRST>`.
- **`||` missing when the rules are on several lines**, which gives two messages for one cause:
  ```
  IgnoreRules.java line …: ';' expected
  IgnoreRules.java line …: not a statement
  ```
  → "Java thought the statement ended at the end of the line. Each new line of the rule needs `||` in front."
- **`endswith` with a small w**: `IgnoreRules.java line …: cannot find symbol`. Java is case-sensitive.
- **`??` without quotes**: `StatusLines.java line …: illegal start of expression` (and several more messages). → Text needs double quotes (m01).
- **Terminal / Git (not from check.sh):**
  - `fatal: not a git repository` → she's in the wrong folder: `pwd`, then `cd` into the project.
  - `! [rejected] main -> main (fetch first)` → she's behind: pull first.
  - Git opens an editor during `pull` → vim: `:wq` + Enter. Next time use `git pull --no-edit`.
  - `403` → permissions on GitHub. Tell the lead; it's not her code.

## Starter cards
- Q: What are Git's three places for a change?
  - working folder (the files)
  - staging area (after git add)
  - history (after git commit)
- Q: In `git status --short`, what do `??`, ` M` and `M ` mean?
  - `??` untracked: never saved
  - ` M` changed, not staged
  - `M ` staged, ready to commit
- Q: What is "behind" and what do you do about it?
  - GitHub has commits you don't have
  - pull first, then push
- Q: What belongs in `.gitignore`? Give 3 examples.
  - build output (target/), IDE files (.idea/), logs, secrets (.env)
  - anything generated or private, not source code
- Q: `x = x + 3;` Which box changes?
  - the one on the LEFT of `=`
  - the right side is only read; it's calculated first
- Q: Git opened an editor during `git pull`. How do you get out?
  - in vim: type `:wq` and press Enter
  - next time: `git pull --no-edit`
