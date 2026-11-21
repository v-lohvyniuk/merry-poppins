# Curriculum

One file per module, named after the module id: `m01_variables.md`, `m02_methods.md`, ...
Start from [_template.md](_template.md).

Rules the tutor relies on:
- The **module id = file name = Java package** under `com.merrypoppins` in `exercises/`.
- Modules are taught in **file-name order**. `_`-prefixed files and this README are ignored.
- **Done when** must be objective. "Tests pass" is decided by `bash scripts/check.sh <module>`, not by the tutor.
- If a module has no curriculum file yet, the tutor tells the learner it isn't ready and adds a note
  under **For reviewer** in `state/progress.md`.

`m00_sample` has no curriculum file. It only exists to check that the Java + Maven toolchain works.
