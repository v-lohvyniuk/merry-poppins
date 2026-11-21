#!/usr/bin/env python3
"""PreToolUse hook for Edit|Write|MultiEdit|NotebookEdit.

Second layer behind the permission rules: the tutor may only create or change
files inside state/. Everything else (exercises, curriculum, scripts, .claude,
root files, anything outside the project) is blocked with exit code 2.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from _common import ROOT, STATE_DIR, block, is_inside, norm, read_input  # noqa: E402


def main() -> None:
    data = read_input()
    tool = data.get("tool_name", "?")
    tool_input = data.get("tool_input") or {}
    raw = tool_input.get("file_path") or tool_input.get("notebook_path") or tool_input.get("path")

    if not raw:
        block(f"{tool} call without a file path; cannot verify it targets state/.")

    target = norm(raw, base=data.get("cwd") or ROOT)

    if is_inside(target, STATE_DIR):
        sys.exit(0)

    try:
        shown = os.path.relpath(target, norm(ROOT)).replace("\\", "/")
    except ValueError:  # different drive on Windows
        shown = raw
    if shown.startswith(".."):
        shown = raw

    if is_inside(target, ROOT / "exercises"):
        why = ("exercise code is written by the learner. You may read and run it, never change it. "
               "Give her the next hint (nudge -> point to line -> pseudo-code) instead.")
    else:
        why = "the tutor may only write files inside state/."
    block(f"{tool} on '{shown}' is not allowed: {why}")


if __name__ == "__main__":
    main()
