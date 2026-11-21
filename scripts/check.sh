#!/usr/bin/env bash
# Run the tests of ONE exercise module and print a short, readable result.
#
# Usage:   bash scripts/check.sh <module>
# Example: bash scripts/check.sh m00_sample
#
# <module> is the package name under com.merrypoppins (exercises/src/*/java/com/merrypoppins/<module>).
# Exit codes: 0 = all tests passed, 1 = tests failed, 2 = compile error, 3 = setup/usage problem.

set -u

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
EX="$ROOT/exercises"
MODULE="${1:-}"

if [[ ! "$MODULE" =~ ^m[0-9]{2}_[a-z0-9_]+$ ]]; then
  echo "Usage: bash scripts/check.sh <module>   (e.g. m00_sample)"
  echo "Modules available:"
  ls "$EX/src/test/java/com/merrypoppins" 2>/dev/null | sed 's/^/  /'
  exit 3
fi

if [[ ! -d "$EX/src/test/java/com/merrypoppins/$MODULE" ]]; then
  echo "Result: SETUP PROBLEM - no tests found for module '$MODULE'"
  echo "Expected folder: exercises/src/test/java/com/merrypoppins/$MODULE"
  exit 3
fi

if ! command -v mvn >/dev/null 2>&1; then
  echo "Result: SETUP PROBLEM - Maven (mvn) is not installed or not on PATH."
  echo "Install JDK 21 and Maven, then open a new terminal (see README.md)."
  exit 3
fi

PY="$(command -v python || command -v python3 || command -v py || true)"
if [[ -z "$PY" ]]; then
  echo "Result: SETUP PROBLEM - Python is not installed or not on PATH."
  exit 3
fi

LOG="$EX/target/check-$MODULE.log"
mkdir -p "$EX/target"
rm -rf "$EX/target/surefire-reports"

cd "$EX" || exit 3
mvn -B -q test \
  -Dtest="com/merrypoppins/$MODULE/**/*" \
  -Dsurefire.failIfNoSpecifiedTests=false \
  >"$LOG" 2>&1
MVN_EXIT=$?

"$PY" - "$MODULE" "$EX" "$LOG" "$MVN_EXIT" <<'PY'
import sys, re, glob, os
import xml.etree.ElementTree as ET

module, ex, log, mvn_exit = sys.argv[1], sys.argv[2], sys.argv[3], int(sys.argv[4])
out = []
p = out.append

def short(path):
    path = path.replace("\\", "/")
    i = path.find("src/")
    return path[i:] if i >= 0 else path

reports = glob.glob(os.path.join(ex, "target", "surefire-reports", "TEST-*.xml"))
log_text = open(log, encoding="utf-8", errors="replace").read() if os.path.exists(log) else ""

if not reports and mvn_exit != 0:
    errors = []
    for line in log_text.splitlines():
        m = re.search(r"\[ERROR\]\s+(\S+\.java):\[(\d+),(\d+)\]\s+(.*)", line)
        if m:
            errors.append(f"  {short(m.group(1))} line {m.group(2)}: {m.group(4).strip()}")
    p(f"Module: {module}")
    if errors:
        p(f"Result: COMPILE ERROR ({len(errors)} problem(s)) - the code could not be built, so no tests ran.")
        p("Note: one broken file anywhere in exercises/ stops ALL modules from building.")
        seen = []
        for e in errors:
            if e not in seen:
                seen.append(e)
        out.extend(seen[:10])
        if len(seen) > 10:
            p(f"  ... and {len(seen) - 10} more")
        code = 2
    else:
        p("Result: BUILD PROBLEM - Maven failed before running tests. Last lines of the log:")
        tail = [l for l in log_text.splitlines() if l.strip()][-15:]
        out.extend("  " + l for l in tail)
        code = 3
    print("\n".join(out))
    sys.exit(code)

total = failed = errored = skipped = 0
problems = []
for rep in sorted(reports):
    root = ET.parse(rep).getroot()
    suites = [root] if root.tag == "testsuite" else root.findall("testsuite")
    for suite in suites:
        for tc in suite.findall("testcase"):
            total += 1
            cls = tc.get("classname", "").split(".")[-1]
            name = f"{cls}.{tc.get('name')}"
            f = tc.find("failure")
            e = tc.find("error")
            if tc.find("skipped") is not None:
                skipped += 1
            elif f is not None or e is not None:
                node = f if f is not None else e
                if f is not None:
                    failed += 1
                else:
                    errored += 1
                msg = (node.get("message") or node.get("type") or "").strip()
                msg = " ".join(msg.split())
                if len(msg) > 300:
                    msg = msg[:300] + "..."
                kind = "FAILED" if f is not None else "ERROR (crashed)"
                # first stack line that points into her code
                where = ""
                for line in (node.text or "").splitlines():
                    m = re.search(r"\((\w+\.java):(\d+)\)", line)
                    if m and "com.merrypoppins" in line:
                        where = f" [{m.group(1)} line {m.group(2)}]"
                        break
                problems.append(f"  [{kind}] {name}{where}\n      {msg}")

passed = total - failed - errored - skipped
p(f"Module: {module}")
if total == 0:
    p("Result: NO TESTS RAN - check that test classes end with 'Test' and methods have @Test.")
    print("\n".join(out)); sys.exit(1)
status = "PASS" if failed == errored == 0 and mvn_exit == 0 else "FAIL"
p(f"Result: {status} ({total} tests: {passed} passed, {failed} failed, {errored} errors, {skipped} skipped)")
out.extend(problems)
print("\n".join(out))
sys.exit(0 if status == "PASS" else 1)
PY
