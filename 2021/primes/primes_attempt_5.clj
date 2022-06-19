(ns primes-attempt-5
  "Clojure implementation of Sieve of Eratosthenes by Alex Vear (axvr)

  This implementation is fast and faithful to Dave's original implementation."
  (:import [java.time Instant Duration]))


;;;; Second version using custom bit-array.  Very complicated and just shy of
;;;; java.util.BitSet performance.


;; Disable overflow checks on mathematical ops and warn when compiler is unable
;; to optimise correctly.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)


(set! *print-length* 20)


(defmacro << [n shift]
   `(bit-shift-left ~n ~shift))


(defmacro >> [n shift]
  `(unsigned-bit-shift-right ~n ~shift))


(defmacro sqr [n]
  `(unchecked-multiply-int ~n ~n))


(def ^:const shift (int 6))
(def ^:const width (int (Math/pow 2 shift)))


(defmacro bget [array idx]
  `(let [slot# (>> ~idx shift)
         bit#  (- ~idx (<< slot# shift))]
     (bit-test (aget ~array slot#) bit#)))


;; (defmacro bset [array idx]
;;   `(let [slot# (>> ~idx shift)
;;          bit#  (- ~idx (<< slot# shift))
;;          val#  (aget ~array slot#)]
;;      (aset ~array slot# ^Long (bit-set val# bit#))))


(defmacro btest [sval bit]
  `(let [mask# (bit-xor ~sval Long/MAX_VALUE)
         bitm# (<< 1 ~bit)]
     (== bitm# (bit-and ~sval (bit-or bitm# mask#)))))


(defmacro bset [sval bit]
  `(bit-or ~sval (<< 1 ~bit)))



;; (uk.axvr.debug/decompile
(defn sieve [^long limit]
  (let [q (inc (long (Math/sqrt limit)))
        ^longs sieve (long-array (inc (>> limit 7)))]
    (loop [factor 3]
      (when (< factor q)
        (let [sidx (>> factor 7)
              bidx (rem (>> factor 1) width)
              sval (aget sieve sidx)]
          (when-not (btest sval bidx)
            (let [factor*2 (<< factor 1)]
              (loop [num      (sqr factor)
                     slot-idx sidx
                     slot-val sval]
                (if (< num limit)
                  (let [sidx     slot-idx
                        slot-idx (>> num 7)
                        bit-idx  (rem (>> num 1) width)
                        sval     (if (== slot-idx sidx)
                                   slot-val
                                   (do
                                     (aset sieve sidx slot-val)
                                     (aget sieve slot-idx)))]
                    (recur (+ factor*2 num)
                           slot-idx
                           (long (bset sval bit-idx))))
                  (aset sieve slot-idx slot-val))))))
        (recur (+ 2 factor))))
    sieve))
;; )


;; FIXME
;; (defn sieve->primes [^longs sieve]
;;   (let [out  (transient [2])
;;         size (count sieve)]
;;     (loop [idx (int 1)]
;;       (when (< idx size)
;;         (when-not (aget sieve idx)
;;           (conj! out (inc (<< idx 1))))
;;         (recur (inc idx))))
;;     (persistent! out)))


(comment

  (uk.axvr.debug/quick-bench
    (bit-set 0x12 10))

  (uk.axvr.debug/quick-bench
    (bset 0x12 10))

  (uk.axvr.debug/quick-bench
    (bit-test 0x12 0))

  (uk.axvr.debug/quick-bench
    (btest 0x12 0))

  ;; 0101
  ;; 1111
  ;;    1
  ;; XOR 0101 + 1111 -> 1010
  ;; OR  (<< 1 bit)  |  ^    -> 1011
  ;; AND 0101 & 1011 -> 0001 = (<< 1 bit)
  ;;
  ;; XOR 0101 + 1111 -> 1010
  ;; OR  (<< 1 bit)  |  ^    -> 1010
  ;; AND 0101 & 1010 -> 0000 not= (<< 1 bit)

  (uk.axvr.debug/quick-bench
    (sieve-new/sieve 1000000))

  (uk.axvr.debug/quick-bench
    (sieve-1-bit/sieve 1000000))

  (uk.axvr.debug/quick-bench
    (sieve-8-bit/sieve 1000000))

  (defmacro bset [slot idx]
    `(bit-set ~slot ))

  (let [out (long-array 4)]
    (loop [slot1 0
           slot2 0
           slot3 0
           slot4 0]
      (if ,,,
        (do
          ,,,
          (cond
            0 (recur (bit-set slot0 ,,,) slot1 slot2 slot3)
            1 (recur slot0 (bit-set slot1 ,,,) slot2 slot3)
            2 (recur slot0 slot1 (bit-set slot2 ,,,) slot3)
            3 (recur slot0 slot1 slot2 (bit-set slot3 ,,,))))
        (do
          (aset out 0 slot0)
          (aset out 1 slot1)
          (aset out 2 slot2)
          (aset out 3 slot3)))))

  )


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
  (let [limit      1000000
        start-time (Instant/now)
        end-by     (+ (.toEpochMilli start-time) 5000)]
    (loop [pass 1]
      (let [sieve    (sieve limit)
            cur-time (System/currentTimeMillis)]
        (if (<= cur-time end-by)
          (recur (inc pass))
          ;; Return benchmark report.
          (let [finished-at (Instant/now)
                unpacked-sieve
                (cons 2 (keep #(when-not (bget ^longs sieve (>> ^long % 1)) %)
                              (range 3 limit 2)))]
            {:primes unpacked-sieve
             :passes pass
             :limit  limit
             :time   (Duration/between start-time finished-at)
             :count  (count unpacked-sieve)
             :valid? (= (count unpacked-sieve)
                        (prev-results limit))}))))))


;; Reenable overflow checks on mathematical ops and turn off warnings.
(set! *warn-on-reflection* false)
(set! *unchecked-math* false)


(defn format-results
  "Format benchmark results into expected output."
  [{:keys [primes passes valid?] :as result}]
  (let [nanos (.toString (.toNanos (:time result)))
        timef (str (subs nanos 0 1) "." (subs nanos 1))]
    (str "Passes: " passes ", "
         "Time: " timef ", "
         "Avg: " (float (/ (/ (.toNanos (:time result)) 1000000000) passes)) ", "
         "Limit: " (:limit result) ", "
         "Count: " (:count result) ", "
         "Valid: " (if valid? "True" "False")
         "\n"
         "axvr_clj_1-bit;" passes ";" timef ";1;algorithm=base,faithful=yes,bits=1")))


(defn run [& _]
  (println (format-results (benchmark)))
  (flush))
