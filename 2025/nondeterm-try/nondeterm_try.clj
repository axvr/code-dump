(ns nondeterm-try)

(def +amb-fail-err+
  (AssertionError. "Amb: fail"))

(defmacro fail []
  `(throw +amb-fail-err+))

(defmacro amb-assert
  "A much faster version of Clojure's `assert` macro which provides significant
  performance wins for complex solves."
  [condition]
  `(when-not ~condition (fail)))

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

;; TODO: disallow IO


;; Basic examples.

(amb-let [?a #{1 2 3}
          ?b #{3 4 5}]
 (amb-assert (= 6 (+ ?a ?b)))
 [?a ?b])
;; => [1 5]

(amb-let [?a #{1 2 3}
          ?b #{3 4 5}]
 (amb-assert (even? ?a))
 (amb-assert (even? ?b))
 (amb-assert (= 6 (+ ?a ?b)))
 [?a ?b])
;; => [2 4]


;; Example from Amb page on Rosetta Code.

(defn str-adjacent? [w1 w2]
  (= (last w1) (first w2)))

(amb-let [?w1 #{"the" "that" "a"}
          ?w2 #{"frog" "elephant" "thing"}
          ?w3 #{"walked" "treaded" "grows"}
          ?w4 #{"slowly" "quickly"}]
 (amb-assert (and (str-adjacent? ?w1 ?w2)
                  (str-adjacent? ?w2 ?w3)
                  (str-adjacent? ?w3 ?w4)))
 [?w1 ?w2 ?w3 ?w4])
;; => ["that" "thing" "grows" "slowly"]


;; 3x3 magic square solver.

(time
 (let [r (mapv int (range 1 10))]
   ;; The order of these impacts the solve time.  Least significant cell to
   ;; most significant = fastest.
   (amb-let [?h r, ?f r, ?d r, ?b r, ?g r, ?c r, ?i r, ?a r, ?e r]
    (amb-assert (and (distinct? ?a ?b ?c ?d ?e ?f ?g ?h ?i)
                     (= (+ ?a ?b ?c)
                        (+ ?a ?d ?g)
                        (+ ?a ?e ?i)
                        (+ ?b ?e ?h)
                        (+ ?c ?e ?g)
                        (+ ?c ?f ?i)
                        (+ ?d ?e ?f)
                        (+ ?g ?h ?i))))
     [[?a ?b ?c]
      [?d ?e ?f]
      [?g ?h ?i]])))
;; => [[2 9 4] [7 5 3] [6 1 8]]

;; Most significant cells:
;;   4: e
;;   3: a i / c g
;;   2: b d f h

;; TODO: generic n-dimensional magic square solver?


;; Wordle solver.

(require '[clojure.string :as str])
(import '(java.util Collection))

;; TODO: create a version of `amb-let` that can return all solutions.

(let [dict (-> "/usr/share/dict/words" slurp str/lower-case (str/split #"\n") set)]
  (amb-let [?word dict]
    (amb-assert (= 5 (count ?word)))
    (let [[?c1 ?c2 ?c3 ?c4 ?c5 :as ?chars] (seq ?word)]
      ;; Encode known information here.

      ;; Invalid words:
      (amb-assert (not (#{"morga" "grush" "calli" "tould" "niuan"
                          "inrub" "punti" "arioi" "umiri" "baioc"}
                        ?word)))

      ;; Yellow and green chars:
      (amb-assert (Collection/.containsAll ?chars #{\e \s \i}))

      ;; Grey chars:
      (amb-assert (not (some #{\a \r \t \h \l \m \p \o \n \c} ?chars)))

      ;; Green chars:
      (amb-assert (and (= ?c1 \s)
                       (= ?c2 \i)
                       (= ?c3)
                       (= ?c4)
                       (= ?c5 \e)))

      ;; Yellow char locations:
      (amb-assert (and (not (#{\e} ?c1))
                       (not (#{\e} ?c2))
                       (not (#{\s} ?c3))
                       (not (#{\e} ?c4))
                       (not (#{} ?c5)))))
    ?word))
;; => "siege"
