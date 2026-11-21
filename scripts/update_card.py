#!/usr/bin/env python3
"""Record an answer to a card and reschedule it.

  correct -> interval doubles (1 -> 2 -> 4 -> 8 ...; capped at 64 days)
  wrong   -> interval resets to 1 day

due_date = today + new interval. One history entry is appended per call.

Usage:
  python scripts/update_card.py m01-001 correct
  python scripts/update_card.py m01-001 wrong
  python scripts/update_card.py m01-001 correct --date 2026-12-01
"""
import argparse
from pathlib import Path

import cards_lib as lib


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("card_id")
    ap.add_argument("result", choices=["correct", "wrong"])
    ap.add_argument("--date", help="treat this day (YYYY-MM-DD) as today")
    ap.add_argument("--file", type=Path, default=lib.DEFAULT_CARDS_PATH, help=argparse.SUPPRESS)
    args = ap.parse_args()

    today = lib.parse_day(args.date)
    data = lib.load(args.file)

    card = next((c for c in data["cards"] if c["id"] == args.card_id), None)
    if card is None:
        lib.fail(f"no card with id '{args.card_id}'")

    old_interval = int(card["interval_days"])
    if args.result == "correct":
        new_interval = min(max(old_interval, 1) * 2, lib.MAX_INTERVAL_DAYS)
    else:
        new_interval = 1

    card["interval_days"] = new_interval
    card["due_date"] = lib.add_days(today, new_interval)
    card["history"].append({"date": today.isoformat(), "result": args.result})
    lib.save(args.file, data)

    lib.emit({
        "id": card["id"],
        "result": args.result,
        "old_interval_days": old_interval,
        "new_interval_days": new_interval,
        "next_due": card["due_date"],
    })


if __name__ == "__main__":
    main()
