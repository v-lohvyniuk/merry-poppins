"""Shared helpers for the spaced-repetition card scripts.

Card format (state/cards.json):
{
  "cards": [
    {
      "id": "m01-001",
      "module": "m01_variables",
      "question": "What is a variable?",
      "expected_answer": ["a named box that holds a value", "has a type"],
      "interval_days": 1,
      "due_date": "2026-11-22",
      "history": [{"date": "2026-11-21", "result": "correct"}]
    }
  ]
}

expected_answer holds key points, not exact wording. Cards are graded on meaning.
Standard library only, so it runs on any Python 3.9+.
"""
from __future__ import annotations

import json
import os
import sys
import tempfile
from datetime import date, timedelta
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
DEFAULT_CARDS_PATH = ROOT / "state" / "cards.json"

MAX_INTERVAL_DAYS = 64  # doubling stops here so old cards still come back ~every 2 months
REQUIRED_FIELDS = ("id", "module", "question", "expected_answer", "interval_days", "due_date", "history")


def fail(message: str, code: int = 1) -> None:
    print(f"ERROR: {message}", file=sys.stderr)
    sys.exit(code)


def parse_day(value: str | None) -> date:
    if value is None:
        return date.today()
    try:
        return date.fromisoformat(value)
    except ValueError:
        fail(f"bad date '{value}', expected YYYY-MM-DD")
        raise  # unreachable, keeps type checkers happy


def load(path: Path) -> dict:
    if not path.exists():
        fail(f"cards file not found: {path}")
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except json.JSONDecodeError as exc:
        fail(f"{path} is not valid JSON: {exc}")
    if not isinstance(data, dict) or not isinstance(data.get("cards"), list):
        fail(f'{path} must look like {{"cards": [...]}}')
    for i, card in enumerate(data["cards"]):
        missing = [f for f in REQUIRED_FIELDS if f not in card]
        if missing:
            fail(f"card #{i} ({card.get('id', '?')}) is missing fields: {', '.join(missing)}")
    return data


def save(path: Path, data: dict) -> None:
    """Write atomically so a crash never leaves a half-written cards.json."""
    path.parent.mkdir(parents=True, exist_ok=True)
    fd, tmp = tempfile.mkstemp(dir=path.parent, prefix=".cards-", suffix=".tmp")
    try:
        with os.fdopen(fd, "w", encoding="utf-8", newline="\n") as fh:
            json.dump(data, fh, indent=2, ensure_ascii=False)
            fh.write("\n")
        os.replace(tmp, path)
    except BaseException:
        if os.path.exists(tmp):
            os.remove(tmp)
        raise


def add_days(day: date, days: int) -> str:
    return (day + timedelta(days=days)).isoformat()


def emit(obj) -> None:
    """Print JSON as UTF-8 regardless of the console code page (Windows)."""
    text = json.dumps(obj, indent=2, ensure_ascii=False) + "\n"
    sys.stdout.buffer.write(text.encode("utf-8"))
    sys.stdout.flush()
