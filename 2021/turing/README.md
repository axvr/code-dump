# Turing Machine simulator

_28th February 2021_

```clojure
(require 'turing-machine)

(def binary-increment-rules
  {:a {nil {:write nil
            :state :b
            :move  1}
       0 {:write 0
          :state :a
          :move  -1}
       1 {:write 1
          :state :a
          :move  -1}}
   :b {nil {:write 1
            :state :c
            :move  -1}
       0 {:write 1
          :state :c
          :move  1}
       1 {:write 0
          :state :b
          :move  1}}
   :c {nil {:write nil
            :state :halt
            :move  1}
       0 {:write 0
          :state :c
          :move  -1}
       1 {:write 1
          :state :c
          :move  -1}}
   :halt nil})

(turing-machine/simulate [1 1 0 1] :a binary-increment-rules 0 nil)
```

_Public domain.  No rights reserved._
