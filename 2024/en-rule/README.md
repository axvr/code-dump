# En-rule

*2024-01-02 – 2024-03-06*

_Public domain.  No rights reserved._

Experiments in extending the Clojure rules engine [O'Doyle Rules](https://github.com/oakes/odoyle-rules)
with some missing [Clara](https://www.clara-rules.org/) features
(i.e. inspection and doc-strings) and other oddities.

> [!WARNING]
> This was an experiment; there are bugs (see `FIXME`s in the source).
>
> This code is not guaranteed to work long term as it abuses O'Doyle to add
> features it was not designed for.  It would be far better were they
> implemented properly in a fork of O'Doyle.

Included functions and macros:

- `ruleset+` replaces `o/ruleset`, allowing metadata to be attached to rules
  and retaining the original rule source for reporting.
- `add-rules` replaces `o/add-rule`, adds rules into the session (or creates
  a new one if not provided) with observer instrumentation attached to them to
  track fact changes by those rules.
- `fire-rules+` replaces `o/fire-rules`, executes the rules with the
  fact-change instrumentation turned on.
- `inspect` returns a map containing the session, everything that occurred each
  time `fire-rules+` was invoked, and the rules themselves (for any rules
  defined with `ruleset+`).
- `ns-qualify-keys` can be used to automatically convert a map of non-qualified
  keys into one that is (as required by O'Doyle).  Useful when loading initial
  facts into the session from maps that don't contain qualified keywords.

A usage example can be found in the `comment` block at the end of the file.
