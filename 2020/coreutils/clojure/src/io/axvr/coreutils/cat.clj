;;;; Public domain.  No rights reserved.

(ns io.axvr.coreutils.cat
  "Port of the Unix `cat' utility to Clojure.")

(defn print-stdin
  ([] (print-stdin (java.io.BufferedReader. *in*)))
  ([reader] (doseq [ln (line-seq reader)]
              (println ln))))

(defn print-file [fname]
  (with-open [rdr (clojure.java.io/reader fname)]
    (print-stdin rdr)))

(defn -main [& args]
  (if (empty? args)
    (print-stdin)
    (doseq [fname args]
      (print-file fname))))
