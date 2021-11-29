(ns new-sieve
  "Clojure implementation of Sieve of Eratosthenes by Alex Vear (axvr)

  This implementation is fast and faithful to Dave's original implementation."
  (:import [java.time Instant Duration]))


;;; Public domain.  2021-11-28


;; Disable overflow checks on mathematical ops and warn when compiler is unable
;; to optimise correctly.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(set! *print-length* 20)


;; This macro exists to improve readability of the `sieve` function.  It's
;; a macro rather than a function to improve performance.
(defmacro doubl
  "Efficiently double a number."
  [n]
  `(bit-shift-left ~n 1))


;; This macro exists to improve readability of the `sieve` function.  It's
;; a macro rather than a function to improve performance.
(defmacro halve
  "Efficiently halve a number."
  [n]
  `(unsigned-bit-shift-right ~n 1))


(defmacro << [& n]
  `(bit-shift-left ~@n))


(defmacro >> [& n]
  `(unsigned-bit-shift-right ~@n))


(def ^:const shift 6)
(def ^:const width 64)


;; (defprotocol IBitArray
;;   (bit-get [this idx])
;;   (bit-set [this idx])
;;   (bit-clear [this idx]))


;; TODO: macro: on-index to eliminate duplicate slot calculations.
;; (deftype BitArray [^longs array]
;;   IBitArray
;;   (bit-get [this idx]
;;     (let [idx  (long idx)
;;           slot (>> idx shift)
;;           bit  (rem idx width)]
;;       (bit-test (aget array slot) bit)))
;;   (bit-set [this idx]
;;     (let [idx  (long idx)
;;           slot (>> idx shift)
;;           bit  (rem idx width)
;;           val  (aget array slot)]
;;       (aset array slot ^long (clojure.core/bit-set val bit))))
;;   (bit-clear [this idx]
;;     (let [idx  (long idx)
;;           slot (>> idx shift)
;;           bit  (rem idx width)
;;           val  (aget array slot)]
;;       (aset array slot ^long (clojure.core/bit-clear val bit)))))


(defn tag [var tag]
  (with-meta var {:tag tag}))


(defmacro bit-get [array idx]
  (let [idx   (tag idx long)
        array (tag array longs)]
    `(let [slot# (>> ~idx shift)
           bit#  (rem ~idx width)]
       (bit-test (aget ~array slot#) bit#))))


(defmacro bit-set [array idx]
  (let [idx   (tag idx long)
        array (tag array longs)]
    `(let [slot# (>> ~idx shift)
           bit#  (rem ~idx width)
           val#  (aget ~array slot#)]
       (aset ~array slot# ^Long (clojure.core/bit-set val# bit#)))))


(defn sieve3 [^long limit]
  (let [q (inc (Math/sqrt limit))
        ^longs sieve (BitArray. (long-array (inc (>> limit 7))))]
    ;; Highly optimised Sieve of Eratosthenes algorithm.
    (loop [factor 3]
      (when (< factor q)
        (if-not (bit-get sieve (halve factor))
          (let [factor*2 (doubl factor)]
            (loop [num (* factor factor)]
              (when (<= num limit)
                (bit-set sieve (halve num))
                (recur (+ num factor*2))))))
        (recur (+ 2 factor))))
    ;; Return sequence of found prime numbers.
    sieve
    #_(cons 2 (->> (range 3 limit 2)
                 (map (fn [^long n]
                        (if-not (bit-get sieve (halve n)) n)))
                 (filter some?)))))


;; TODO: integrate more complex array logic into here.
(defn sieve2 [^long limit]
  (let [q (inc (Math/sqrt limit))
        ^longs sieve (long-array (inc (>> limit 7)))]
    ;; Highly optimised Sieve of Eratosthenes algorithm.
    (loop [factor 3]
      (when (< factor q)
        (if-not (bit-get sieve (halve factor))
          (let [factor*2 (doubl factor)]
            (loop [num (* factor factor)]
              (when (<= num limit)
                (bit-set sieve (halve num))
                (recur (+ num factor*2))))))
        (recur (+ 2 factor))))
    ;; Return sequence of found prime numbers.
    sieve
    #_(cons 2 (->> (range 3 limit 2)
                 (map (fn [^long n]
                        (if-not (bit-get sieve (halve n)) n)))
                 (filter some?)))))


;; (defn sieve [^long limit]
;;   (let [q (inc (Math/sqrt limit))
;;         ^booleans sieve (make-array Boolean/TYPE (halve limit))]
;;     ;; Highly optimised Sieve of Eratosthenes algorithm.
;;     (loop [factor 3]
;;       (when (< factor q)
;;         (if-not (aget sieve (halve factor))
;;           (let [factor*2 (doubl factor)]
;;             (loop [num (* factor factor)]
;;               (when (<= num limit)
;;                 (aset sieve (halve num) true)
;;                 (recur (+ num factor*2))))))
;;         (recur (+ 2 factor))))
;;     ;; Return sequence of found prime numbers.
;;     (cons 2 (->> (range 3 limit 2)
;;                  (map (fn [^long n]
;;                         (if-not (aget sieve (halve n)) n)))
;;                  (filter some?)))))


(def prev-results
  "Previous results to check against sieve results."
  {10          4
   100         25
   1000        168
   10000       1229
   100000      9592
   1000000     78498
   10000000    664579
   100000000   5761455
   1000000000  50847534
   10000000000 455052511})


(defn benchmark
  "Benchmark Sieve of Eratosthenes algorithm."
  []
  (let [limit       1000000
        start-time  (Instant/now)
        end-by      (+ (.toEpochMilli start-time) 5000)]
    (loop [pass 1]
      (let [primes   (sieve limit)
            cur-time (System/currentTimeMillis)]
        (if (<= cur-time end-by)
          (recur (inc pass))
          ;; Return benchmark report.
          {:primes primes
           :passes pass
           :limit  limit
           :time   (Duration/between start-time (Instant/now))
           :valid? (= (count primes)
                      (prev-results limit))})))))


;; Reenable overflow checks on mathematical ops and turn off warnings.
(set! *warn-on-reflection* false)
(set! *unchecked-math* false)


(defn format-results
  "Format benchmark results into expected output."
  [{:keys [primes passes limit time valid?]}]
  (let [nanos (.toString (.toNanos time))
        timef (str (subs nanos 0 1) "." (subs nanos 1))]
    (str "Passes: " passes ", "
         "Time: " timef ", "
         "Avg: " (float (/ (/ (.toNanos time) 1000000000) passes)) ", "
         "Limit: " limit ", "
         "Count: " (count primes) ", "
         "Valid: " (if valid? "True" "False")
         "\n"
         "axvr_clj-sln-2_1-bit;" passes ";" timef ";1;algorithm=base,faithful=yes,bits=1")))


(defn run [{:keys [warm-up?]
            :or   {warm-up? false}}]
  (when warm-up?
    ;; Warm-up reduces the variability of results.
    (format-results (benchmark)))
  (println (format-results (benchmark))))
