# Convert 24-bit colours to closest xterm-256 colour

_February 2021_

Tool to find the best xterm colour for a true colour (24-bit colour) value.

```clojure
(load-file "colours.clj")
(ns 'colours)
(find-closest "#88766F" xterm-colours)
```

_Public domain.  No rights reserved._
