;;;; Public domain.  No rights reserved.

(ns io.axvr.coreutils.yes
  "Port of the Unix `yes' utility to Clojure.")

(defn yes
  ([] (yes "y"))
  ([& txt]
   (let [text (clojure.string/join " " txt)]
     (loop []
       (println text)
       (recur)))))

(defn -main [& args]
  (apply yes args))
