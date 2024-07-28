(ns one-brc
  "Slow Clojure solution for the 1 billion row challenge.
  https://github.com/gunnarmorling/1brc"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.core.reducers :as r]))

(let [time*
      (fn [^long duration-in-ms f]
        (let [^com.sun.management.ThreadMXBean bean (java.lang.management.ManagementFactory/getThreadMXBean)
              bytes-before (.getCurrentThreadAllocatedBytes bean)
              duration (* duration-in-ms 1000000)
              start (System/nanoTime)
              first-res (f)
              delta (- (System/nanoTime) start)
              deadline (+ start duration)
              tight-iters (max (quot (quot duration delta) 10) 1)]
          (loop [i 1]
            (let [now (System/nanoTime)]
              (if (< now deadline)
                (do (dotimes [_ tight-iters] (f))
                    (recur (+ i tight-iters)))
                (let [i' (double i)
                      bytes-after (.getCurrentThreadAllocatedBytes bean)
                      t (/ (- now start) i')]
                  (println
                   (format "Time per call: %s   Alloc per call: %,.0fb   Iterations: %d"
                           (cond (< t 1e3) (format "%.0f ns" t)
                                 (< t 1e6) (format "%.2f us" (/ t 1e3))
                                 (< t 1e9) (format "%.2f ms" (/ t 1e6))
                                 :else (format "%.2f s" (/ t 1e9)))
                           (/ (- bytes-after bytes-before) i')
                           i))
                  first-res))))))]
  (defmacro time+
    "Like `time`, but runs the supplied body for 2000 ms and prints the average
    time for a single iteration. Custom total time in milliseconds can be provided
    as the first argument. Returns the returned value of the FIRST iteration."
    [?duration-in-ms & body]
    (let [[duration body] (if (integer? ?duration-in-ms)
                            [?duration-in-ms body]
                            [2000 (cons ?duration-in-ms body)])]
      `(~time* ~duration (fn [] ~@body)))))


(defprotocol MetricOps
  (add-temp [this temp])
  (combine-metric [this metric]))

(defrecord Metric [count min max sum]
  MetricOps
  (add-temp [this temp]
            (->Metric (unchecked-inc count)
                      (clojure.core/min min temp)
                      (clojure.core/max max temp)
                      (unchecked-add sum temp)))
  (combine-metric [this metric]
                  (->Metric (unchecked-add count (:count metric))
                            (clojure.core/min min (:min metric))
                            (clojure.core/max max (:max metric))
                            (unchecked-add sum (:sum metric)))))

(defn record [metrics station temp]
  (if-let [metric (get @metrics station)]
    (send metric add-temp temp)
    (vswap! metrics assoc station (agent (->Metric 1 temp temp temp)))))

(defn process-line [metrics line]
  (let [[station temp] (str/split line #";" 2)]
    (record metrics station (parse-double temp))
    metrics))

(defn process-file [file]
  (let [f (io/file file)]
    (with-open [r (io/reader f)]
      (reduce process-line
              (volatile! {})
              (line-seq r)))))

(comment
  (time+
   ;; 16.96 us for 10 rows.
   (process-file "example.txt"))

  (time
   ;; 1001584.509833 msecs for 1B rows.
   (process-file "/Users/axvr/Projects/Experiments/1brc/measurements.txt"))

  (time
   ;; 9.77 secs for 10M rows.
   (process-file "/Users/axvr/Projects/Experiments/1brc/measurements.txt"))
  )


(defn record-2 [metric temp]
  (if metric
    (add-temp metric temp)
    (->Metric 1 temp temp temp)))

(defn process-file-2 [file]
  (let [f (io/file file)]
    (with-open [r (io/reader f)]
      (r/fold 8192
              (fn
                ([] {})
                ([x y]
                 (merge-with combine-metric x y)))
              (fn
                ([] {})
                ([acc record]
                 (let [[station temp] (str/split record #";" 2)]
                   (update acc station record-2 (parse-double temp)))))
              ;; FIXME: this is probably the bottleneck
              (line-seq r)))))

(comment
  (time+
   ;; 13.20 us for 10 rows.
   (process-file-2 "example.txt"))

  (time
   ;; 369349.788708 msecs for 1B rows.
   (process-file-2 "/Users/axvr/Projects/Experiments/1brc/measurements.txt"))

  (time+
   ;; 3.63 s for 10M rows.
   (process-file-2 "/Users/axvr/Projects/Experiments/1brc/measurements.txt"))
  )
