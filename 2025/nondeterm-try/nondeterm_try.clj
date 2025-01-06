(ns nondeterm-try)

(defmacro fail []
  `(throw (AssertionError. "amb-fail!")))

(defmacro amb-let
  "Simple nondeterminism using macros and try/catch as Clojure lacks
  continuations."
  [[amb ambs & binds] & body]
  `((fn [ambs#]
      (when (empty? ambs#) (fail))
      (let [res# (try
                  ((fn [~amb]
                     ~(if (seq binds)
                        `(amb-let ~binds ~@body)
                        `(do ~@body)))
                   (first ambs#))
                  (catch AssertionError _# ::failed))]
        (if (identical? res# ::failed)
          (recur (next ambs#))
          res#)))
    ~ambs))

(amb-let [?a #{1 2 3}
          ?b #{3 4 5}]
 (assert (= 6 (+ ?a ?b)))
 [?a ?b])
;; => [1 5]

(amb-let [?a #{1 2 3}
          ?b #{3 4 5}]
 (assert (even? ?a))
 (assert (even? ?b))
 (assert (= 6 (+ ?a ?b)))
 [?a ?b])
;; => [2 4]

;; TODO: disallow IO
