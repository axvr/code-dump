# On Lisp non-determinism in Clojure

_2024-07-05_

_Public domain.  No rights reserved._

Failed attempt at replicating the non-determinism macros from the book _On
Lisp_ to Clojure.

I believe this was the second attempt.  I was trying to make it integrate well
into Clojure, by using dynamic vars, but unfortunately I wasn't able to get the
macro expansions to work correctly.  Later I came to the realisation that
[exceptions](../../2025/nondeterm-try) could achieve the desired result.
