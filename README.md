# user.clj

Automatically loads a global `user.clj` file on REPL/Clojure start, if one
exists at `~/.clojure/user.clj` or `~/.config/clojure/user.clj`.

Just place the following in your global `deps.edn` file.

```clojure
;;;; ~/.clojure/deps.edn or ~/.config/clojure/deps.edn
{:deps {uk.axvr/user {:git/url "https://github.com/axvr/user.clj.git"
                      :git/sha "12e9c0dc2dbd27ae68b5a9f7be830931a532cd2f"}}
 ,,,}
```


## Legal

*No Rights Reserved.*

All source code, documentation and associated files packaged with
`uk.axvr.user` are dedicated to the public domain.  A full copy of the CC0
(Creative Commons Zero 1.0 Universal) public domain dedication should have been
provided with this extension in the `COPYING` file.

The author is not aware of any patent claims which may affect the use,
modification or distribution of this software.
