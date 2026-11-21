#!/usr/bin/env python3
"""Add a new spaced-repetition card. New cards are due tomorrow with a 1-day interval.

Usage:
  python scripts/add_card.py --module m01_variables \
      --question 'What is the difference between int and String?' \
      --point 'int holds whole numbers' \
      --point 'String holds text in double quotes'

--point can be repeated: each is one key idea the answer must contain (meaning, not wording).
The id is generated as <module prefix>-<number>, e.g. m01-004, unless --id is given.
Use single quotes around text so the shell does not interpret $ or backticks.
"""
import argparse
import re
from pathlib import Path

import cards_lib as lib

MODULE_RE = re.compile(r"^m\d{2}_[a-z0-9_]+$")


def next_id(cards: list, module: str) -> str:
    prefix = module.split("_", 1)[0]  # m01_variables -> m01
    nums = [
        int(m.group(1))
        for c in cards
        if (m := re.fullmatch(rf"{re.escape(prefix)}-(\d+)", c["id"]))
    ]
    return f"{prefix}-{(max(nums) + 1) if nums else 1:03d}"


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--module", required=True, help="module id, e.g. m01_variables")
    ap.add_argument("--question", required=True)
    ap.add_argument("--point", action="append", required=True, help="one key point of the expected answer (repeatable)")
    ap.add_argument("--id", help="explicit card id (default: generated)")
    ap.add_argument("--date", help="treat this day (YYYY-MM-DD) as today")
    ap.add_argument("--file", type=Path, default=lib.DEFAULT_CARDS_PATH, help=argparse.SUPPRESS)
    args = ap.parse_args()

    if not MODULE_RE.match(args.module):
        lib.fail(f"module '{args.module}' should look like m01_variables")
    points = [p.strip() for p in args.point if p.strip()]
    if not args.question.strip() or not points:
        lib.fail("question and at least one --point are required")

    today = lib.parse_day(args.date)
    data = lib.load(args.file)

    # Avoid near-duplicates: same module + same question text.
    q_norm = " ".join(args.question.lower().split())
    for c in data["cards"]:
        if c["module"] == args.module and " ".join(c["question"].lower().split()) == q_norm:
            lib.fail(f"card {c['id']} already asks this question")

    card_id = args.id or next_id(data["cards"], args.module)
    if any(c["id"] == card_id for c in data["cards"]):
        lib.fail(f"card id '{card_id}' already exists")

    card = {
        "id": card_id,
        "module": args.module,
        "question": args.question.strip(),
        "expected_answer": points,
        "interval_days": 1,
        "due_date": lib.add_days(today, 1),
        "history": [],
    }
    data["cards"].append(card)
    lib.save(args.file, data)
    lib.emit({"added": card})


if __name__ == "__main__":
    main()
