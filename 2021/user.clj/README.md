# user.clj

*8th August 2021 – 16th August 2021*

Automatically loads a global `user.clj` file on REPL/Clojure start, if one
exists at `~/.clojure/user.clj` or `~/.config/clojure/user.clj`.

This was moved here from a full repository (on 2021-11-03) because I discovered
that user-level/global Clojure configs are not handled as expected (i.e. it all
gets packages with your application).  As such, this library is no longer used.

<!--
Just place the following in your global `deps.edn` file.

```clojure
;;;; ~/.clojure/deps.edn or ~/.config/clojure/deps.edn
{:deps {uk.axvr/user {:git/url "https://github.com/axvr/user.clj.git"
                      :git/sha "12e9c0dc2dbd27ae68b5a9f7be830931a532cd2f"}}
 ,,,}
```
-->

*Public domain.  No Rights Reserved.*
