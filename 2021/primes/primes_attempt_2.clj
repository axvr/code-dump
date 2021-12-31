(ns primes-attempt-2
  "Clojure implementation of Sieve of Eratosthenes by Alex Vear (axvr)"
  (:import [java.time Instant Duration]))


;;;; First fast version.


(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)


(defn sieve [size]
  (let [size  (int size)
        sieve (boolean-array size true)
        q     (int (inc (Math/sqrt size)))]
    (loop [factor 3]
      (when (< factor q)
        (when (aget sieve factor)
          (loop [num (* factor factor)]
            (when (<= num size)
              (aset sieve num false)
              (recur (+ num factor factor)))))
        (recur (+ 2 factor))))
    (cons 2 (->> (range 3 size 2)
                 (map #(when (aget sieve %) %))
                 (filter some?)))))


(def prev-results
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


(defn benchmark []
  (let [size        1000000
        start-time  (Instant/now)
        start-milli (.toEpochMilli start-time)]
    (loop [pass 1]
      (let [result   (sieve size)
            cur-time (System/currentTimeMillis)]
        (if (< (- cur-time start-milli) 5000)
          (recur (inc pass))
          {:result result
           :passes pass
           :size   size
           :time   (Duration/between start-time (Instant/now))
           :valid? (= (count result)
                      (prev-results size))})))))


(set! *warn-on-reflection* false)
(set! *unchecked-math* false)


;; TODO: comments?


(defn format-results
  "Format benchmark results into expected output."
  [{:keys [result passes size time valid?]}]
  (let [nanos (.toString (.toNanos time))
        timef (str (subs nanos 0 1) "." (subs nanos 1))]
    (str "Passes: " passes ", Time: " timef
         ", Avg: " (float (/ (/ (.toNanos time) 1000000000) passes))
         ", Limit: " size ", Count: " (count result) ", Valid: " valid?
         "\n"
         "axvr;" passes ";" timef ";1;algorithm=base,faithful=yes")))


(defn run [_]
  (println (format-results (benchmark))))
