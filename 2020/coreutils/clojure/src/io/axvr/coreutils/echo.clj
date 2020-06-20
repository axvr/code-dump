;;;; Public domain.  No rights reserved.

(ns io.axvr.coreutils.echo
  "Port of the Unix `echo' utility to Clojure.")

(defn -main [& args]
  (println (clojure.string/join " " args)))
