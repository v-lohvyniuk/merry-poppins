#!/usr/bin/env python3
"""Streaks and badges for the tutor. Switched on/off by ONE flag: "enabled" in gamification.json.

  python scripts/rewards.py status        one line: streak, best streak, rest days left, badge count
  python scripts/rewards.py wrapup        record today, award automatic badges, print what's new
  python scripts/rewards.py award <id>    award a tutor badge (only ids listed in gamification.json)
  python scripts/rewards.py badges        list earned and still-open badges

When "enabled" is false every command prints "REWARDS DISABLED" and does nothing visible.
(Practice days are still recorded quietly, so the streak isn't lost if it's switched back on.)

A practice day is any day with at least one of:
  - a review card answered            (state/cards.json history)
  - a session logged by /wrap-up      (state/progress.md session log)
  - a tutor session of N+ minutes      (recorded by the session-clock hook; N = active_minutes)
Rest days: up to rest_days_per_week missed days per calendar week (Mon-Sun) don't break the streak.
Wrong answers, failing tests and hints NEVER affect anything here.

Standard library only (Python 3.9+). The tutor must never edit state/rewards.json directly.
"""
from __future__ import annotations

import argparse
import json
import os
import re
import sys
import tempfile
from datetime import date, timedelta
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
CONFIG_FILE = ROOT / "gamification.json"
STATE_FILE = ROOT / "state" / "rewards.json"
CARDS_FILE = ROOT / "state" / "cards.json"
PROGRESS_FILE = ROOT / "state" / "progress.md"
DISABLED = "REWARDS DISABLED"

DEFAULTS = {
    "enabled": False,
    "rest_days_per_week": 2,
    "active_minutes": 10,
    "streak_badges": [3, 7, 14, 30, 60, 100],
    "mastered_card_interval_days": 16,
    "mastered_card_badges": [10, 25, 50, 100],
    "milestones": {},
    "tutor_badges": {},
    "bug_bounties_found": 0,
}


# ---------- files ----------

def load_config() -> dict:
    try:
        data = json.loads(CONFIG_FILE.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        data = {}
    cfg = dict(DEFAULTS)
    cfg.update({k: v for k, v in data.items() if k in DEFAULTS})
    cfg["enabled"] = data.get("enabled") is True  # anything but a real true means off
    return cfg


def load_state() -> dict:
    try:
        data = json.loads(STATE_FILE.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        data = {}
    data.setdefault("active_days", [])
    data.setdefault("badges", {})
    return data


def save_state(state: dict) -> None:
    STATE_FILE.parent.mkdir(parents=True, exist_ok=True)
    state["active_days"] = sorted(set(state["active_days"]))
    fd, tmp = tempfile.mkstemp(dir=STATE_FILE.parent, prefix=".rewards-", suffix=".tmp")
    try:
        with os.fdopen(fd, "w", encoding="utf-8", newline="\n") as fh:
            json.dump(state, fh, indent=2, ensure_ascii=False)
            fh.write("\n")
        os.replace(tmp, STATE_FILE)
    except BaseException:
        if os.path.exists(tmp):
            os.remove(tmp)
        raise


def say(text: str) -> None:
    sys.stdout.buffer.write((text + "\n").encode("utf-8"))
    sys.stdout.flush()


# ---------- facts ----------

def _iso(value) -> date | None:
    try:
        return date.fromisoformat(str(value)[:10])
    except ValueError:
        return None


def practice_days(state: dict) -> set:
    days = {d for d in (_iso(x) for x in state["active_days"]) if d}
    try:
        for card in json.loads(CARDS_FILE.read_text(encoding="utf-8")).get("cards", []):
            for h in card.get("history", []):
                d = _iso(h.get("date"))
                if d:
                    days.add(d)
    except (OSError, json.JSONDecodeError, AttributeError):
        pass
    try:
        for m in re.finditer(r"^### Session \d+ · (\d{4}-\d{2}-\d{2})", PROGRESS_FILE.read_text(encoding="utf-8"), re.M):
            d = _iso(m.group(1))
            if d:
                days.add(d)
    except OSError:
        pass
    return days


def current_module_number() -> int | None:
    try:
        m = re.search(r"\*\*Current module:\*\*\s*`?m(\d{2})_", PROGRESS_FILE.read_text(encoding="utf-8"))
        return int(m.group(1)) if m else None
    except OSError:
        return None


def mastered_cards(cfg: dict) -> int:
    try:
        cards = json.loads(CARDS_FILE.read_text(encoding="utf-8")).get("cards", [])
        return sum(1 for c in cards if int(c.get("interval_days", 0)) >= cfg["mastered_card_interval_days"])
    except (OSError, json.JSONDecodeError, ValueError, AttributeError):
        return 0


def _week(d: date):
    return d.isocalendar()[:2]


def run_ending(days: set, end: date, allowance: int) -> dict:
    """The streak that ends on `end`: walk back over practice days, spending rest days per week."""
    if not days:
        return {"length": 0, "start": None, "rests": {}}
    first = min(days)
    d, length, kept_length, start = end, 0, 0, None
    rests, kept_rests = {}, {}
    while d >= first:
        if d in days:
            length += 1
            kept_length, start, kept_rests = length, d, dict(rests)
        else:
            wk = _week(d)
            if rests.get(wk, 0) >= allowance:
                break
            rests[wk] = rests.get(wk, 0) + 1
            length += 1
        d -= timedelta(days=1)
    # rest days spent before the first practice day of the run don't count
    return {"length": kept_length, "start": start, "rests": kept_rests}


def summary(cfg: dict, state: dict, today: date) -> dict:
    days = practice_days(state)
    allowance = int(cfg["rest_days_per_week"])
    done_today = today in days
    end = today if done_today else today - timedelta(days=1)
    run = run_ending(days, end, allowance)
    best = max((run_ending(days, d, allowance)["length"] for d in days), default=0)
    rests_used = run["rests"].get(_week(today), 0) if run["length"] else 0
    return {
        "streak": run["length"],
        "best": max(best, run["length"]),
        "done_today": done_today,
        "rest_left_this_week": max(allowance - rests_used, 0),
        "badges": len(state["badges"]),
        "days": days,
    }


# ---------- badges ----------

def automatic_badges(cfg: dict, state: dict, s: dict, today: date) -> list:
    """(id, name) of automatic badges earned now and not yet recorded."""
    new = []
    for n in sorted(int(x) for x in cfg["streak_badges"]):
        if s["best"] >= n:
            new.append((f"streak_{n}", f"{n}-day streak"))
    mastered = mastered_cards(cfg)
    for n in sorted(int(x) for x in cfg["mastered_card_badges"]):
        if mastered >= n:
            new.append((f"cards_{n}", f"{n} cards remembered for {cfg['mastered_card_interval_days']}+ days"))
    cur = current_module_number()
    if cur is not None:
        for mod, name in sorted(cfg["milestones"].items()):
            m = re.match(r"^m(\d{2})", mod)
            if m and cur > int(m.group(1)):
                new.append((f"milestone_{mod}", name))
    # Comeback: practising again after a streak ended. Kind, not guilt.
    earlier = [d for d in s["days"] if d < today]
    if s["done_today"] and s["streak"] == 1 and earlier and s["best"] >= 3:
        new.append(("comeback", "Comeback: back at it after a break"))
    return [(i, n) for i, n in new if i not in state["badges"]]


# ---------- commands ----------

def record_active(minutes: int, today: date | None = None) -> None:
    """Called by the session-clock hook. Records the day once the session is long enough. Silent."""
    cfg = load_config()
    if minutes < int(cfg["active_minutes"]):
        return
    state = load_state()
    day = (today or date.today()).isoformat()
    if day not in state["active_days"]:
        state["active_days"].append(day)
        save_state(state)


def status_line(today: date | None = None) -> str | None:
    """One line for the session-start context, or None when disabled."""
    cfg = load_config()
    if not cfg["enabled"]:
        return None
    s = summary(cfg, load_state(), today or date.today())
    today_txt = "yes" if s["done_today"] else "not yet (any practice today counts, even 5 minutes of cards)"
    return (f"Rewards: streak {s['streak']} day(s), best {s['best']} · practised today: {today_txt} · "
            f"rest days left this week: {s['rest_left_this_week']} · badges earned: {s['badges']}.")


def cmd_status(today: date) -> None:
    line = status_line(today)
    say(line if line else DISABLED)


def cmd_wrapup(today: date) -> None:
    cfg = load_config()
    state = load_state()
    if today.isoformat() not in state["active_days"]:
        state["active_days"].append(today.isoformat())
    if not cfg["enabled"]:
        save_state(state)
        say(DISABLED)
        return
    s = summary(cfg, state, today)
    new = automatic_badges(cfg, state, s, today)
    for badge_id, _ in new:
        state["badges"][badge_id] = today.isoformat()
    save_state(state)
    say(f"🔥 Streak: {s['streak']} day(s) (best {s['best']}). Rest days left this week: {s['rest_left_this_week']}.")
    for _, name in new:
        say(f"🏅 New badge: {name}")


def cmd_award(badge_id: str, today: date) -> None:
    cfg = load_config()
    if not cfg["enabled"]:
        say(DISABLED)
        return
    badge = cfg["tutor_badges"].get(badge_id)
    if not badge:
        say(f"ERROR: '{badge_id}' is not a tutor badge. Allowed: {', '.join(sorted(cfg['tutor_badges'])) or 'none'}")
        sys.exit(1)
    state = load_state()
    if badge_id in state["badges"]:
        say(f"Already earned on {state['badges'][badge_id]}: {badge['name']}")
        return
    state["badges"][badge_id] = today.isoformat()
    save_state(state)
    say(f"🏅 New badge: {badge['name']}")


def cmd_badges(today: date) -> None:
    cfg = load_config()
    if not cfg["enabled"]:
        say(DISABLED)
        return
    state = load_state()
    earned = state["badges"]
    say(f"Badges earned: {len(earned)}")
    names = {f"streak_{n}": f"{n}-day streak" for n in cfg["streak_badges"]}
    names.update({f"cards_{n}": f"{n} cards remembered" for n in cfg["mastered_card_badges"]})
    names.update({f"milestone_{k}": v for k, v in cfg["milestones"].items()})
    names.update({k: v["name"] for k, v in cfg["tutor_badges"].items()})
    names["comeback"] = "Comeback: back at it after a break"
    for badge_id, day in sorted(earned.items(), key=lambda kv: kv[1]):
        say(f"  ✅ {names.get(badge_id, badge_id)} ({day})")
    open_ = [n for i, n in names.items() if i not in earned]
    if open_:
        say("Still to earn: " + "; ".join(open_))
    if int(cfg["bug_bounties_found"]) > 0:
        say(f"🐞 Bug bounties found: {cfg['bug_bounties_found']}")


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("command", choices=["status", "wrapup", "award", "badges"])
    ap.add_argument("badge_id", nargs="?")
    ap.add_argument("--today", help=argparse.SUPPRESS)  # for the reviewer's tests only
    args = ap.parse_args()
    today = _iso(args.today) if args.today else date.today()
    if today is None:
        say("ERROR: bad --today date")
        sys.exit(1)
    if args.command == "award":
        if not args.badge_id:
            say("ERROR: award needs a badge id")
            sys.exit(1)
        cmd_award(args.badge_id, today)
    elif args.command == "status":
        cmd_status(today)
    elif args.command == "wrapup":
        cmd_wrapup(today)
    else:
        cmd_badges(today)


if __name__ == "__main__":
    main()
