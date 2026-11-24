#!/usr/bin/env python3
"""Session clock for the tutor.

  session_clock.py start  (SessionStart hook)
      Records the start time in state/.session.json, unless a session is still
      open from < 3 hours ago (restart/resume keeps the original start).
      Adds factual context: date, time, due cards, current module, next step.

  session_clock.py tick   (UserPromptSubmit hook)
      Adds "[session clock] N min elapsed" to each learner message.

Never blocks anything: always exits 0.
"""
import json
import os
import re
import sys
from datetime import date, datetime, timedelta

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from _common import ROOT, read_input  # noqa: E402

sys.path.insert(0, str(ROOT / "scripts"))
try:
    import rewards  # noqa: E402  (streaks/badges; switched on/off in gamification.json)
except Exception:  # noqa: BLE001 - rewards must never break the clock
    rewards = None

SESSION_FILE = ROOT / "state" / ".session.json"
PROGRESS_FILE = ROOT / "state" / "progress.md"
CARDS_FILE = ROOT / "state" / "cards.json"
RESUME_WINDOW = timedelta(hours=3)
TARGET_MIN, TARGET_MAX = 75, 90


def now() -> datetime:
    return datetime.now().astimezone().replace(microsecond=0)


def load_session() -> dict:
    try:
        return json.loads(SESSION_FILE.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return {}


def started_at(session: dict):
    try:
        return datetime.fromisoformat(session["started_at"])
    except (KeyError, TypeError, ValueError):
        return None


def emit(event: str, text: str) -> None:
    out = {"hookSpecificOutput": {"hookEventName": event, "additionalContext": text}}
    sys.stdout.buffer.write(json.dumps(out, ensure_ascii=False).encode("utf-8"))


def progress_field(text: str, label: str) -> str:
    m = re.search(rf"\*\*{re.escape(label)}:\*\*\s*(.+)", text)
    return m.group(1).strip() if m else "unknown"


def due_count() -> str:
    try:
        cards = json.loads(CARDS_FILE.read_text(encoding="utf-8"))["cards"]
        today = date.today().isoformat()
        return str(sum(1 for c in cards if c.get("due_date", "9999") <= today))
    except Exception:  # noqa: BLE001 - context only, never fail
        return "unknown (could not read state/cards.json)"


def start() -> None:
    t = now()
    session = load_session()
    began = started_at(session)
    resumed = bool(began and not session.get("ended_at") and t - began < RESUME_WINDOW)
    if not resumed:
        began = t
        try:
            SESSION_FILE.write_text(json.dumps({"started_at": t.isoformat()}, indent=2) + "\n", encoding="utf-8")
        except OSError:
            pass

    try:
        progress = PROGRESS_FILE.read_text(encoding="utf-8")
    except OSError:
        progress = ""
    elapsed = int((t - began).total_seconds() // 60)
    lines = [
        "Tutor session info (from session_clock hook):",
        f"- Today: {t:%Y-%m-%d (%A)}, local time {t:%H:%M}.",
        (f"- Session resumed: started {began:%H:%M}, {elapsed} min elapsed."
         if resumed else f"- New session started at {t:%H:%M}. Target length {TARGET_MIN}-{TARGET_MAX} min."),
        f"- Review cards due today: {due_count()}.",
        f"- Current module: {progress_field(progress, 'Current module')}.",
        f"- Next step recorded last time: {progress_field(progress, 'Next session starts with')}.",
        f"- Sessions so far: {progress_field(progress, 'Sessions so far')}.",
    ]
    if rewards is not None:
        try:
            line = rewards.status_line()
            if line:
                lines.append(f"- {line}")
        except Exception:  # noqa: BLE001
            pass
    emit("SessionStart", "\n".join(lines))


def tick() -> None:
    session = load_session()
    began = started_at(session)
    if not began:
        return
    minutes = int((now() - began).total_seconds() // 60)
    if rewards is not None:
        try:
            rewards.record_active(minutes)
        except Exception:  # noqa: BLE001
            pass
    text = f"[session clock] {minutes} min elapsed (target {TARGET_MIN}-{TARGET_MAX} min)."
    if session.get("ended_at"):
        text += " /wrap-up already ran for this session."
    elif minutes >= TARGET_MAX + 5:
        text += " Well past the target length."
    elif minutes >= TARGET_MIN:
        text += " Target length reached."
    emit("UserPromptSubmit", text)


def main() -> None:
    read_input()  # drain stdin
    mode = sys.argv[1] if len(sys.argv) > 1 else ""
    try:
        if mode == "start":
            start()
        elif mode == "tick":
            tick()
    except Exception:  # noqa: BLE001 - the clock must never break a session
        pass
    sys.exit(0)


if __name__ == "__main__":
    main()
