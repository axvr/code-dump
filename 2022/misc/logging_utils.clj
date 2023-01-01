;; Useful Clojure functions for logging in the middle of threading macros.
;; 2022-01-17  Public domain.  No rights reserved.

(ns logging-utils)

(defn |>
  "Perform a side-effect operation on an intermediate value within
  a thread-first macro.  Useful for logging."
  [x f]
  (f x)
  x)

(comment
  (-> 1
      (+ 2)
      (|> #(prn :+2 %))
      (* 5))
  )

(defn |>>
  "Perform a side-effect operation on an intermediate value within
  a thread-last macro.  Useful for logging."
  [f x]
  (f x)
  x)

(comment
  (->> [1 2 3]
       (filter even?)
       (|>> #(prn :evens %))
       (map inc))
  )
