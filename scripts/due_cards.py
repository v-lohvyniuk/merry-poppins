#!/usr/bin/env python3
"""Print the spaced-repetition cards that are due (due_date <= today) as JSON.

Usage:
  python scripts/due_cards.py                    # due today
  python scripts/due_cards.py --limit 7          # at most 7, most overdue first
  python scripts/due_cards.py --date 2026-12-01  # pretend today is another day
  python scripts/due_cards.py --module m01_variables
"""
import argparse
from pathlib import Path

import cards_lib as lib


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--date", help="treat this day (YYYY-MM-DD) as today")
    ap.add_argument("--module", help="only cards from this module")
    ap.add_argument("--limit", type=int, default=0, help="max cards to return (0 = all)")
    ap.add_argument("--file", type=Path, default=lib.DEFAULT_CARDS_PATH, help=argparse.SUPPRESS)
    args = ap.parse_args()

    today = lib.parse_day(args.date)
    data = lib.load(args.file)

    due = [
        c for c in data["cards"]
        if c["due_date"] <= today.isoformat() and (not args.module or c["module"] == args.module)
    ]
    # Most overdue first; among equals, cards she got wrong more often first.
    due.sort(key=lambda c: (c["due_date"], -sum(1 for h in c["history"] if h.get("result") == "wrong")))
    total_due = len(due)
    if args.limit > 0:
        due = due[: args.limit]

    lib.emit({
        "today": today.isoformat(),
        "due_total": total_due,
        "returned": len(due),
        "cards": [
            {
                "id": c["id"],
                "module": c["module"],
                "question": c["question"],
                "expected_answer": c["expected_answer"],
                "interval_days": c["interval_days"],
                "due_date": c["due_date"],
                "times_wrong": sum(1 for h in c["history"] if h.get("result") == "wrong"),
                "times_seen": len(c["history"]),
            }
            for c in due
        ],
    })


if __name__ == "__main__":
    main()
