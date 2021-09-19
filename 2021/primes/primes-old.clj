(ns primes
  (:import [java.time Instant Duration]))


;; (set! *print-length* 30)


;; Num passes made by other solutions.

;; Java 1 - 5600–5700
;; Java 2 - 2960
;; Java 3 - 5850
;; Java 4 - N/A

;; Julia 1 - 6300
;; Julia 2 - 6800–6900
;; Julia 3 - 9100–9400

;;   Py 2 - 5400
;;   Py 3 - 6000 (not faithful)

;;   CL 2 - 3740 / 5200




;; ;; ~0.303s
;; (defn sieve
;;   ([limit]
;;    (sieve '(2) (range 3 (inc limit) 2) (Math/sqrt limit) limit))
;;   ([primes [p & ps] check-up-to limit]
;;    (if (and (seq ps) (<= p check-up-to))
;;      (recur (conj primes p)
;;             (remove (set (range (+ p p) limit p)) ps)
;;             check-up-to
;;             limit)
;;      (flatten (concat (conj primes p) ps)))))


;; (count (sieve 1000000))
;; (time (sieve 1000000))
;; ;; FIXME: slow.





;; (defn filter-multiples [p ps limit]
;;   (loop [res '()
;;          xs ps
;;          next-multiple (iterate #(+ p %) (* p p))]
;;     (let [mult (first next-multiple)
;;           x    (first xs)]
;;       (if (seq xs)
;;         (if (> x mult)
;;           (recur res xs (rest next-multiple))
;;           (if (= x mult)
;;             (recur res
;;                    (rest xs)
;;                    (rest next-multiple))
;;             (recur (conj res x)
;;                    (rest xs)
;;                    next-multiple)))
;;         res))))


;; ;; ~0.139s
;; ;; FIXME: Broken.
;; (defn sieve [limit]
;;   (let [check-up-to (Math/sqrt limit)]
;;     (loop [primes '(2)
;;            [p & ps] (range 3 (inc limit) 2)]
;;       (if (<= p check-up-to)
;;         (recur (cons p primes)
;;                (filter-multiples p ps limit))
;;         (into (cons p primes) ps)))))


;; (time (count (sieve 1000000)))
;; (time (sieve 1000000))





;; (defn new-sieve [size]
;;   (->> (cycle [true false])
;;        lazy-seq
;;        (cons true)
;;        (cons false)
;;        (take size)
;;        (into-array Boolean)))


;; ;; FIXME: somehow even slower...  ~14s
;; (defn sieve [^Integer size]
;;   (let [q     (int (Math/sqrt size))
;;         sieve (new-sieve size)]
;;     (doall
;;       (for [factor (range 3 (inc size) 2)
;;             num    (range (* factor factor) size (* 2 factor))
;;             :when  (odd? num)
;;             :while (< factor q)]
;;         (aset sieve (dec num) false)))
;;     sieve))


;; ;; ~8s.
;; (defn sieve [^Integer size]
;;   (let [q     (int (Math/sqrt size))
;;         sieve (new-sieve size)]
;;     (doseq [factor (range 3 (inc size) 2)
;;             :while (< factor q)
;;             :when  (aget sieve (dec factor))]
;;       (doseq [num (range (* factor factor) (inc size) (* 2 factor))
;;               :when (odd? num)]
;;         (aset sieve (dec num) false)))
;;     sieve))


;; (defn new-sieve [size]
;;   (->> (cycle [true false])
;;        lazy-seq
;;        (cons true)
;;        (cons false)
;;        (boolean-array size)))

;; (time (new-sieve 1000000))
;; (time (boolean-array 1000000 true))


;; (defn sieve [^Long size]
;;   (let [reduced-size (int (/ (dec size) 2))
;;         sieve        (boolean-array reduced-size true)
;;         reduced-q    (/ (dec (int (Math/sqrt size))) 2)]
;;     (doseq [factor (range 1 (inc reduced-q))
;;             :when (aget sieve factor)]
;;       (doseq [num (range (* 2 factor (inc factor))
;;                          reduced-size
;;                          (+ factor factor 1))]
;;         (aset sieve num false)))
;;     (keep-indexed (fn [i x]
;;                     (cond
;;                       (zero? i) 2
;;                       x (+ i i 1))) (next sieve))))


;; (seq (sieve 30))

;; (time (sieve 1000000))
;; (time (count (sieve 1000000)))





;; TODO: goal: 1000 passes per second with a faithful implementation.
;;   Hopefully not impossible...
;; TODO: parallel version.  Goal: 4000 passes per second.


;; ;; ~3.6s
;; (defn sieve [^Integer size]
;;   (let [sieve        (java.util.BitSet.)
;;         q            (int (Math/sqrt size))]
;;     (loop [factor (.nextClearBit sieve 2)]
;;       (if (<= factor q)
;;         (do
;;           (doseq [num (range (* factor factor) size factor)]
;;             (.set sieve num true))
;;           (recur (.nextClearBit sieve (inc factor))))
;;         (filter some? (map #(if (.get sieve %) nil %) (range 2 size)))))))

;; (time (sieve 30))
;; (time (sieve 1000000))
;; (time (count (sieve 1000000)))




;; Possible optimisations:
;;   - Skip even numbers.


;; ;; ~20ms (15–35ms)  ~468 passes
;; (defn sieve [^Integer size]
;;   (let [sieve (java.util.BitSet.)
;;         q     (int (Math/sqrt size))]
;;     (loop [factor (.nextClearBit sieve 2)]
;;       (if (<= factor q)
;;         (do
;;           (doseq [^Integer num (range (* factor factor) size factor)]
;;             (.set sieve num true))
;;           (recur (.nextClearBit sieve (inc factor))))
;;         (filter some? (map #(if (.get sieve %) nil %) (range 2 size)))))))

;; (time (sieve 30))
;; (time (sieve 1000000))
;; (time (count (sieve 1000000)))








;; ;; 516 passes.  Hacky skipping of some even numbers.
;; (defn sieve [^Integer size]
;;   (let [sieve (java.util.BitSet.)
;;         q     (int (Math/sqrt size))]
;;     (loop [factor (.nextClearBit sieve 3)]
;;       (if (<= factor q)
;;         (do
;;           (doseq [^Integer num (range (* factor factor) size factor)]
;;             (.set sieve num true))
;;           (recur (.nextClearBit sieve (inc factor))))
;;         (cons 2 (filter some? (map #(if (.get sieve %) nil %) (range 3 size 2))))))))

;; (time (sieve 30))
;; (time (sieve 1000000))
;; (time (count (sieve 1000000)))






;; ;; 960 passes.  Skip even numbers.
;; (defn sieve [^Integer size]
;;   (let [sieve (doto (java.util.BitSet.)
;;                 (.set 3 size true))
;;         q     (int (Math/sqrt size))]
;;     (loop [factor 3]
;;       (if (.get sieve factor)
;;         (if (<= factor q)
;;           (do
;;             (doseq [^Integer num (range (* factor factor) size factor)]
;;               (.set sieve num false))
;;             (recur (+ 2 factor)))
;;           (cons 2 (filter some? (map #(when (.get sieve %) %) (range 3 size 2)))))
;;         (recur (+ 2 factor))))))


;; ~1000 passes.  Skip even numbers.
(defn sieve [^Integer size]
  (let [sieve (doto (java.util.BitSet.)
                (.set 3 size true))
        q     (int (Math/sqrt size))]
    (doseq [factor (range 3 (inc q) 2)
            :when (.get sieve factor)]
      (doseq [^Integer num (range (* factor factor) size factor)]
        (.set sieve num false)))
    (cons 2 (filter some? (map #(when (.get sieve %) %) (range 3 size 2))))))


;; (time (sieve 100))
;; (time (sieve 1000000))
;; (time (count (sieve 1000000)))
;; (time (count (sieve 1000)))


;; ;; ~1700–1800 passes.
;; (defn sieve [^Integer size]
;;   (let [sieve (doto (java.util.BitSet.)
;;                 (.set 3 size true))
;;         q     (int (Math/sqrt size))]
;;     (doseq [factor (range 3 (inc q) 2)
;;             :when (.get sieve factor)]
;;       (doseq [^Integer num (range (* factor factor) size (+ factor factor))]
;;         (.set sieve num false)))
;;     (cons 2 (filter some? (map #(when (.get sieve %) %) (range 3 size 2))))))


;; ;; ~2100–2200 passes.
;; (defn sieve [size]
;;   (let [sieve (boolean-array size true)
;;         q     (int (Math/sqrt size))]
;;     (doseq [factor (range 3 (inc q) 2)
;;             :when (aget sieve factor)]
;;       (doseq [^Integer num (range (* factor factor) size (+ factor factor))]
;;         (aset sieve num false)))
;;     (cons 2 (->> (range 3 size 2)
;;                  (map #(when (aget sieve %) %))
;;                  (filter some?)))))

;; ~3600–3800 passes.
(defn sieve [size]
  (let [size  (int size)
        sieve (boolean-array size true)
        q     (int (inc (Math/sqrt size)))]
    (loop [factor 3]
      (if (aget sieve factor)
        (do
          (loop [num (* factor factor)]
            (aset sieve num false)
            (let [nxt-num (+ num factor factor)]
              (when (<= nxt-num size)
                (recur nxt-num))))
          (let [nxt-fac (+ 2 factor)]
            (when (< nxt-fac q)
              (recur nxt-fac))))
        (let [nxt-fac (+ 2 factor)]
          (when (< nxt-fac q)
            (recur nxt-fac)))))
    (cons 2 (->> (range 3 size 2)
                 (map #(when (aget sieve %) %))
                 (filter some?)))))


;; ~4400 passes.
(defn sieve [size]
  (let [size  (int size)
        sieve (boolean-array size true)
        q     (int (inc (Math/sqrt size)))]
    (loop [factor 3]
      (when (aget sieve factor)
        (loop [num (* factor factor)]
          (aset sieve num false)
          (let [nxt-num (+ num factor factor)]
            (when (<= nxt-num size)
              (recur nxt-num)))))
      (let [nxt-fac (+ 2 factor)]
        (when (< nxt-fac q)
          (recur nxt-fac))))
    (cons 2 (->> (range 3 size 2)
                 (map #(when (aget sieve %) %))
                 (filter some?)))))




(defn benchmark []
  (time
    (let [start-time (Instant/now)]
      (loop [i 1]
        (sieve 1000000)
        (if (> 5000 (.toMillis (Duration/between start-time (Instant/now))))
          (recur (inc i))
          i)))))


(defn run [& _]
  (println (benchmark)))

