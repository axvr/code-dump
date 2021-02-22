# Unfinished Rubik's Cube Simulator

_January 2021_

My attempt at building a Rubik's Cube simulator.  It is very much unfinished.
This project taught me lots about [destructuring in Clojure][destructure] and
how to model real-world objects in code which don't cleanly map to classical
data structures.

[destructure]: https://clojure.org/guides/destructuring

Cube rotation is missing which is the final piece to make full Rubik's Cube
simulator possible.  (Without cube rotation you can only rotate one face.)

```clojure
(require 'cube)
(ns cube)

(print-cube (new-cube))
;;         +-------+
;;         | b b b |
;;         | b b b |
;;         | b b b |
;;         +-------+
;;         | w w w |
;;         | w w w |
;;         | w w w |
;; +-------+-------+-------+
;; | o o o | g g g | r r r |
;; | o o o | g g g | r r r |
;; | o o o | g g g | r r r |
;; +-------+-------+-------+
;;         | y y y |
;;         | y y y |
;;         | y y y |
;;         +-------+

(-> (new-cube)
    rotate-face
    rotate-face
    print-cube)
;;         +-------+
;;         | b b b |
;;         | b b b |
;;         | b b b |
;;         +-------+
;;         | w w w |
;;         | w w w |
;;         | y y y |
;; +-------+-------+-------+
;; | o o r | g g g | o r r |
;; | o o r | g g g | o r r |
;; | o o r | g g g | o r r |
;; +-------+-------+-------+
;;         | w w w |
;;         | y y y |
;;         | y y y |
;;         +-------+
```

_Public domain.  No rights reserved._
