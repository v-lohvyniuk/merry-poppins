#!/usr/bin/env python3
"""PreToolUse hook for Bash|PowerShell: strict allowlist for shell commands.

Every part of a chained command (&&, ||, ;, |) must be on the allowlist.
Redirections, background '&', subshells, $(...), backticks and newlines are
always blocked, because they can write files or hide other commands.

Allowed:
  python|python3|py scripts/{due_cards,update_card,add_card}.py [args]
  bash scripts/check.sh <module>   |   scripts/check.sh <module>
  mvn [flags] [clean|compile|test-compile] test   (run inside exercises/)
  git status|log|diff|show [args]                 (read-only)
  ls, pwd, date, echo, head, tail, wc, grep, which
  cd <dir inside the project>
To allow more, edit this file AND permissions.allow in .claude/settings.json.
"""
import os
import re
import shlex
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from _common import ROOT, block, is_inside, norm, read_input  # noqa: E402

CARD_SCRIPTS = {"due_cards.py", "update_card.py", "add_card.py"}
SIMPLE = {"ls", "pwd", "date", "echo", "head", "tail", "wc", "grep", "which"}
GIT_READONLY = {"status", "log", "diff", "show"}
MVN_GOALS = {"clean", "compile", "test-compile", "test"}
MVN_BANNED_FLAGS = {"-f", "--file", "-s", "--settings", "-gs", "--global-settings"}
SEPARATORS = {"&&", "||", ";", "|"}
MODULE_RE = re.compile(r"^m\d{2}_[a-z0-9_]+$")
PUNCT = set("();<>|&")

ALLOWED_HELP = (
    "Allowed: python scripts/due_cards.py|update_card.py|add_card.py ..., bash scripts/check.sh <module>, "
    "mvn test (inside exercises/), git status|log|diff|show, ls, pwd, date, echo, head, tail, wc, grep. "
    "Use one simple command per call, single quotes for text, no redirection (>), no $(...) or backticks."
)


def deny(why: str) -> None:
    block(f"{why}\n{ALLOWED_HELP}")


def resolves_to(word: str, cwd: str, target) -> bool:
    return norm(word, base=cwd) == norm(target)


def check_segment(words: list, cwd: str) -> str:
    """Validate one simple command. Returns the (possibly new) cwd."""
    if not words:
        deny("empty command segment.")
    cmd, args = words[0], words[1:]
    shown = " ".join(words)

    if re.match(r"^[A-Za-z_][A-Za-z0-9_]*=", cmd):
        deny(f"environment-variable prefixes are not allowed: `{shown}`")

    if cmd == "cd":
        if len(args) != 1:
            deny("`cd` needs exactly one folder inside the project.")
        new = norm(args[0], base=cwd)
        if not is_inside(new, ROOT):
            deny(f"`cd` outside the project is not allowed: `{shown}`")
        return new

    if cmd in {"python", "python3", "py"}:
        if not args or not any(resolves_to(args[0], cwd, ROOT / "scripts" / s) for s in CARD_SCRIPTS):
            deny(f"python may only run scripts/due_cards.py, update_card.py or add_card.py: `{shown}`")
        if any(a.startswith("--f") for a in args[1:]):  # --file and its argparse abbreviations
            deny("the --file option is reserved for the reviewer's tests; cards live in state/cards.json.")
        return cwd

    check_sh = ROOT / "scripts" / "check.sh"
    if cmd in {"bash", "sh"} or resolves_to(cmd, cwd, check_sh):
        rest = args[1:] if cmd in {"bash", "sh"} else args
        script = args[0] if cmd in {"bash", "sh"} and args else cmd
        if not resolves_to(script, cwd, check_sh):
            deny(f"only scripts/check.sh may be run this way: `{shown}`")
        if len(rest) > 1 or (rest and not MODULE_RE.match(rest[0])):
            deny(f"check.sh takes one module id like m01_variables: `{shown}`")
        return cwd

    if cmd == "mvn":
        if norm(cwd) != norm(ROOT / "exercises"):
            deny("run Maven inside exercises/ (`cd exercises && mvn test`), or better: `bash scripts/check.sh <module>`.")
        goals = [a for a in args if not a.startswith("-")]
        flags = [a for a in args if a.startswith("-")]
        if "test" not in goals or any(g not in MVN_GOALS for g in goals):
            deny(f"Maven may only run the test phase (optionally with clean/compile): `{shown}`")
        if any(f.split("=")[0] in MVN_BANNED_FLAGS for f in flags):
            deny(f"Maven -f/-s flags are not allowed: `{shown}`")
        return cwd

    if cmd == "git":
        if not args or args[0] not in GIT_READONLY:
            deny(f"only read-only git is allowed (status, log, diff, show). The learner commits herself: `{shown}`")
        if any(a.startswith("--output") or a == "--ext-diff" for a in args):
            deny(f"git options that write files or run programs are not allowed: `{shown}`")
        return cwd

    if cmd in SIMPLE:
        if cmd == "date" and any(a in {"-s", "--set"} or a.startswith("--set=") for a in args):
            deny("`date --set` is not allowed.")
        return cwd

    deny(f"`{cmd}` is not on the tutor's command allowlist: `{shown}`")
    return cwd


def main() -> None:
    data = read_input()
    tool = data.get("tool_name", "")
    if tool != "Bash":
        block(f"the {tool or 'shell'} tool is disabled for the tutor. Use the Bash tool with an allowed command.\n{ALLOWED_HELP}")

    command = (data.get("tool_input") or {}).get("command", "")
    if not command.strip():
        deny("empty command.")
    if "\n" in command or "\r" in command:
        deny("multi-line commands are not allowed. Put everything on one line.")
    for bad in ("`", "$(", "${", "<(", ">("):
        if bad in command:
            deny(f"command substitution / expansion `{bad}` is not allowed.")

    try:
        lex = shlex.shlex(command, posix=True, punctuation_chars=True)
        lex.whitespace_split = True
        tokens = list(lex)
    except ValueError as exc:
        deny(f"could not parse the command ({exc}).")

    cwd = data.get("cwd") or str(ROOT)
    segment = []
    for tok in tokens + [";"]:
        if tok in SEPARATORS:
            if segment:
                cwd = check_segment(segment, cwd)
            elif tok != ";":
                deny("empty part in a chained command.")
            segment = []
        elif tok and set(tok) <= PUNCT:
            deny(f"shell operator `{tok}` is not allowed (no redirection, background jobs or subshells).")
        else:
            segment.append(tok)

    sys.exit(0)


if __name__ == "__main__":
    main()
