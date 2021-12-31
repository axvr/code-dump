(ns primes-attempt-4)


;;;; First attempt at using a custom bit-array.
;;;; 2021-10-20.  Public domain.


;; TODO: sieve already checks bit value, avoid doing it again...


;; Disable overflow checks on mathematical ops and warn when compiler is unable
;; to optimise correctly.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)


(defmacro << [& n]
  `(bit-shift-left ~@n))


(defmacro >> [& n]
  `(bit-shift-right ~@n))


(def ^:const shift 6)
(def ^:const width 64)


(decompile
(binding [*warn-on-reflection* true
          *unchecked-math* :warn-on-boxed]

(defmacro << [& n]
  `(bit-shift-left ~@n))


(defmacro >> [& n]
  `(bit-shift-right ~@n))


(def ^:const shift 6)
(def ^:const width 64)
(defprotocol IBitArray
  (bit-get2   [this idx])
  (bit-set2   [this idx])
  (bit-clear2 [this idx]))


(deftype BitArray [^longs array]
  IBitArray
  (bit-get2 [this idx]
    (let [idx  (long idx)
          slot (>> idx shift)
          bit  (mod idx width)]
      (bit-test (aget array slot) bit)))
  (bit-set2 [this idx]
    (let [idx  (long idx)
          slot (>> idx shift)
          bit  (mod idx width)
          val  (aget array slot)]
      (when-not (bit-test val bit)
        (aset array slot ^long (bit-set val bit)))))
  (bit-clear2 [this idx]
    (let [idx  (long idx)
          slot (>> idx shift)
          bit  (mod idx width)
          val  (aget array slot)]
      (when (bit-test val bit)
        (aset array slot ^long (bit-clear val bit))))))
))


(defn b-get [^longs array ^long idx]
  (let [slot (>> idx shift)
        bit  (mod idx width)]
    (bit-test (aget array slot) bit)))


(defn b-set [^longs array ^long idx]
  (let [slot (>> idx shift)
        bit  (mod idx width)
        val  (aget array slot)]
    (when-not (bit-test val bit)
      (aset array slot ^long (bit-set val bit)))))


(defn b-clr [^longs array ^long idx]
  (let [slot (>> idx shift)
        bit  (mod idx width)
        val  (aget array slot)]
    (when (bit-test val bit)
      (aset array slot ^long (bit-clear val bit)))))


(defn make-bit-array [^long size]
  (make-array Long/TYPE (inc (>> size shift))))


(defn make-bit-array2 [^long size]
  (BitArray. (make-array Long/TYPE (inc (>> size shift)))))


(bench
  (let [limit 1000000
        array (bit-array/make-bit-array2 limit)]
    (doseq [x (range 0 limit)]
      (bit-array/bit-set2 array x)))
  )

(bench
  (let [limit 1000000
        array (bit-array/make-bit-array limit)]
    (doseq [x (range 0 limit)]
      (bit-array/b-set array x)))
  )
