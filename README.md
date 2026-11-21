# Merry Poppins: Java + test automation tutor

An AI tutor that runs inside Claude Code. It teaches Java and test automation in short sessions, quizzes old topics with spaced repetition, and checks exercises by running the tests. It **never writes the exercise code for you**.

---

## For the learner

### One-time setup
1. Install **Git for Windows** (includes Git Bash), **Python 3**, **JDK 21** and **Maven**:
   ```powershell
   winget install Git.Git
   ```
   ```powershell
   winget install Python.Python.3.12
   ```
   ```powershell
   winget install EclipseAdoptium.Temurin.21.JDK
   ```
   ```powershell
   winget install Apache.Maven
   ```
   Close and reopen the terminal afterwards.
2. Open this folder in your editor (IntelliJ IDEA Community is a good choice: *Open* → `exercises/pom.xml` → *Open as Project*).
3. Start Claude Code **in this folder** (`merry-poppins/`, the one with `CLAUDE.md`) and say *hi*. The first session checks that everything works.

### Every session (30–45 minutes)
1. Open Claude Code in this folder and say hi. The tutor starts with a **short quiz**, then continues where you stopped.
2. Learn, try the exercise, and ask for help. Useful commands:

   | Type | What happens |
   |---|---|
   | `/quiz` | review old topics (the tutor does this at the start anyway) |
   | `/lesson` | learn the next concept |
   | `/review` | run the tests for your exercise and get hints or feedback |
   | `/wrap-up` | **always at the end**: saves your progress |

   Prefer Ukrainian? Just say *"Давай українською"*. You can switch back any time.
3. You write the code in your editor. Run the tests yourself any time:
   ```
   bash scripts/check.sh m01_variables
   ```
4. After `/wrap-up`, **commit**:
   ```
   git add -A
   git commit -m "Session N: what I did"
   ```

### Why won't it just give me the answer?
Because you learn by solving it. When you're stuck it gives hints in 3 steps: a nudge, then *where to look*, then the steps in plain words. Being stuck is normal and part of the job.

---

## For the reviewer (lead)

### Weekly review checklist
1. `git log --oneline --since="1 week ago"`: did sessions happen and get committed?
2. Open [state/progress.md](state/progress.md):
   - **For reviewer**: things the tutor flagged for you. Tick `[x]` or delete when handled.
   - **Struggles / revisit**: recurring weak spots. Consider adjusting the curriculum or a 1:1.
   - **Session log**: newest on top (duration, teach-back, quiz score, hint level, mood).
3. `git diff <last-reviewed-commit>..HEAD -- exercises/src/main`: her actual code.
4. `git diff <last-reviewed-commit>..HEAD -- exercises/src/test`: **should normally be empty** (tests are yours).
5. Card health: `python scripts/due_cards.py --date <a date next week>` shows what's coming up.

### Adding a module
1. Copy [curriculum/_template.md](curriculum/_template.md) → `curriculum/mNN_<name>.md`.
2. Create `exercises/src/main/java/com/merrypoppins/mNN_<name>/` (starter code) and `exercises/src/test/java/com/merrypoppins/mNN_<name>/` (your tests).
3. Check: `bash scripts/check.sh mNN_<name>` should FAIL on the starter code and PASS on your private reference solution.
4. Uncomment dependencies in `exercises/pom.xml` when a module needs AssertJ / Selenide / Playwright / REST Assured. Check those versions are current first.
5. Commit. The tutor picks modules up in file-name order.

### Layout
```
CLAUDE.md                 tutor behaviour rules
curriculum/               modules (you write them)
exercises/                Maven project, one package per module
state/progress.md         progress + reviewer notes (tutor writes)
state/cards.json          spaced-repetition cards (changed only via scripts)
scripts/                  due_cards.py, update_card.py, add_card.py, check.sh
.claude/settings.json     permissions + hooks
.claude/hooks/            guard_edit.py, guard_bash.py, session_clock.py
.claude/skills/           lesson, quiz, review, wrap-up
```

### Guardrails (two layers)
| What | Layer 1: `permissions` in settings.json | Layer 2: PreToolUse hooks |
|---|---|---|
| File edits (Write/Edit/MultiEdit/NotebookEdit) | allow `state/**`; deny `exercises/**`, `curriculum/**`, `scripts/**`, `.claude/**`, root files | `guard_edit.py`: anything resolving outside `state/` is blocked (exit 2) |
| Shell (Bash) | allow scripts, `mvn test`, read-only git | `guard_bash.py`: **allowlist**. Every part of a chained command must be allowed. Blocks redirects, `$(…)`, backticks, `sh -c`, `git commit`, etc. |
| PowerShell tool | denied | blocked |
| Bypass / auto permission modes | disabled (`disableBypassPermissionsMode`, `disableAutoMode`) | n/a |

The hooks use `onFailure: "block"`, so if Python is missing or a hook crashes, the action is **blocked**, not allowed.

Known limits: the learner can still change `.claude/settings.json` herself, or launch Claude Code from another folder. The guardrails stop the *tutor*, not her. Git history is your audit trail.

### Changing guardrails
Edit `.claude/settings.json` or `.claude/hooks/*.py` yourself (the tutor can't). To allow a new shell command, add it to `ALLOWED` in `guard_bash.py` **and** to `permissions.allow`.
