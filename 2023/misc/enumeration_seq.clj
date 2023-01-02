;; Example of correct usage of Clojure transients to create a vector of items
;; in a Java enumeration.

;; You aren't supposed to update transients in-place, instead
;; reduce over them using the result of `conj!` as the next value.
(loop [enum (org.bouncycastle.jce.ECNamedCurveTable/getNames)
       tr   (transient [])]
  (if (.hasMoreElements enum)
    (recur enum (conj! tr (.nextElement enum)))
    (persistent! tr)))

;; Somewhat equivalent to:
(enumeration-seq (org.bouncycastle.jce.ECNamedCurveTable/getNames))

