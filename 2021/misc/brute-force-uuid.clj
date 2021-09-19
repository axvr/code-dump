;;;; Random code to see how long it takes to brute force an UUID.
;;;; Created: 2021-08-04
;;;; Public domain.  No rights reserved.

(defn brute-force-uuid-starting-with [match]
  (loop [i 1]
    (let [uuid     (java.util.UUID/randomUUID)
          uuid-str (.toString uuid)]
      (if (clojure.string/starts-with? uuid-str match)
        (println "Found" uuid "in" i "iterations")
        (recur (inc i))))))

(time (brute-force-uuid-starting-with "00000000-0"))
