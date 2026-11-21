"""Shared helpers for the tutor's guardrail hooks. Standard library only."""
from __future__ import annotations

import json
import os
import re
import sys
from pathlib import Path

# Project root = two levels above this file (.claude/hooks/_common.py).
# Derived from the file location, not from env vars, so it can't be redirected.
ROOT = Path(__file__).resolve().parent.parent.parent
STATE_DIR = ROOT / "state"

REVIEWER_NOTE = (
    "This guardrail is intentional. Do not try another way to do the same thing. "
    "If you believe the block is wrong, add a note under 'For reviewer' in state/progress.md."
)


def read_input() -> dict:
    try:
        return json.loads(sys.stdin.read() or "{}")
    except json.JSONDecodeError:
        return {}


def norm(path: str | os.PathLike, base: str | os.PathLike | None = None) -> str:
    """Absolute, symlink-resolved, case-normalised path. Handles Git Bash '/c/...' on Windows."""
    p = str(path).strip().strip('"')
    if os.name == "nt":
        m = re.match(r"^/([a-zA-Z])(/.*)?$", p)
        if m:
            p = f"{m.group(1)}:{m.group(2) or '/'}"
    if not os.path.isabs(p):
        p = os.path.join(str(base) if base else str(ROOT), p)
    return os.path.normcase(os.path.realpath(p))


def is_inside(path: str, folder: str | os.PathLike) -> bool:
    folder_n = norm(folder)
    return path == folder_n or path.startswith(folder_n.rstrip("\\/") + os.sep)


def block(reason: str) -> None:
    """Exit code 2 = block the tool call; stderr is shown to Claude."""
    print(f"BLOCKED by tutor guardrail: {reason}\n{REVIEWER_NOTE}", file=sys.stderr)
    sys.exit(2)
