(ns uk.axvr.user
  (:require [clojure.java.io :as io]))

(def user-conf
  (->> ["/.clojure/user.clj"
        "/.config/clojure/user.clj"]
       (map (partial str (System/getenv "HOME")))
       (map io/file)
       (filter #(. % exists))
       first))

(defn load-user-conf []
  (when user-conf
    (load-file (str user-conf))))
