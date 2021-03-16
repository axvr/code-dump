(ns test
  (:require [hiccup.core :as h]
            [clojure.java.io :as io]))

(defn read-template [tfname]
  (read-string (slurp tfname)))

(let [t (read-template "template.clj")]
  (println (h/html (:content t)))
  (println (h/html (:title t))))

(let [title "foo bar biz"]
  (h/html
    (read-template "base.clj")))

`(let [title "hello"] ~(read-template "base.clj"))

(eval `(let [title "foo"] ~(read-template "base.clj")))

(defn -main [& args]
  (println
    (h/html [:span {:class "foo"} "bar"])))
